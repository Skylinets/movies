package com.skyline.moviescatalog.common.base

import com.skyline.moviescatalog.common.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import okio.IOException
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<ApiState<T>> = flow {

        emit(ApiState.Loading)

        val response = apiCall()
        if(response.isSuccessful){
           val data = response.body()
           if(data != null) {
               emit(ApiState.Success(data))
           }
            else {
                val error = response.errorBody()
               if (error != null){
                   emit(ApiState.Failure(IOException(error.toString())))
               } else {
                   emit(ApiState.Failure(IOException("Something wrong")))
               }
           }
        } else {
            emit(ApiState.Failure(Throwable(IOException(response.errorBody().toString()))))
        }
    }.catch { e->
        e.printStackTrace()
        emit(ApiState.Failure(Exception(e)))
    }.flowOn(Dispatchers.IO)
}
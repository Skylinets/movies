package com.skyline.moviescatalog.features.movies.domain.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.skyline.moviescatalog.R
import com.skyline.moviescatalog.data.model.Movies
import com.skyline.moviescatalog.data.network.ApiService

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel()
) {
    val response = viewModel.response.value

    if(response.isLoading)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
            CircularProgressIndicator()
        }
    if(response.error.isNotEmpty())
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
            Text(text = response.error)
        }
    if(response.data.isNotEmpty()){
       LazyColumn {
           items(
               response.data,
               key = {
                   it.id ?: ""
               }
           ) { response ->
                CardRow(response = response)
           }
       }
    }
}

@Composable
fun CardRow(
    response: Movies.Results
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = CardDefaults.outlinedShape
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${ApiService.BASE_POSTER_URL} ${response.poster_path}")
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build()
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(CenterVertically)
            ) {
                Text(
                    text = response.original_title.orEmpty(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Blue
                    ), textAlign = TextAlign.Center
                )
            }
        }
    }
}
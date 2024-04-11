package com.skyline.moviescatalog.common.base

interface Mapper<F,T> {

    fun fromMap(from: F): T
}
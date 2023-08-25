package com.karakoca.moviecleanarch.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error<out T>(val message: String?, val error: Throwable?) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()
}

package com.bilalberekgm.bluearchive.util


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null

) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String,data: T? = null): Resource<T>(data , message)
    class Loading<T>: Resource<T>()
}


object Util {
    const val BASE_URL = "http://api-blue-archive.vercel.app/"
    const val CONNECTION_TIMEOUT = 60L
}
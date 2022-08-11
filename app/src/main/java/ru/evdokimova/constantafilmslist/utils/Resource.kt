package ru.evdokimova.constantafilmslist.utils

sealed class Resource<T>(
    var data: T? = null,
    val message: String? = null
) {
    class Error<T>(message: String?) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
}
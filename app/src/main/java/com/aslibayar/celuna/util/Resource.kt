package com.aslibayar.celuna.util

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String, val data: T? = null) : Resource<T>()
    data class Loading<T>(val data: T? = null) : Resource<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String, data: T? = null) = Error(message, data)
        fun <T> loading(data: T? = null) = Loading(data)
    }
} 
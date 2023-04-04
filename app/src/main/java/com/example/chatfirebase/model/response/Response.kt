package com.example.chatfirebase.model.response

sealed class Response<out T>(
    val isSuccess: Boolean? = false,
    val message: String? = null,

    ) {
    data class Success<out T>(val data: T?) : Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
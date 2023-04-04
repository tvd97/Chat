package com.example.chatfirebase.model.response

data class Response<T>(
    val isSuccess: Boolean? = false,
    val data: T? = null,
    val message: String? = null
)
package com.example.chatfirebase.model

import androidx.lifecycle.MutableLiveData

data class UserLogin(
    var email: MutableLiveData<String>,
    var password: MutableLiveData<String>
)
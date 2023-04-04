package com.example.chatfirebase.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatfirebase.data.local.SaveLocal
import com.example.chatfirebase.model.User
import com.example.chatfirebase.model.UserLogin
import com.example.chatfirebase.model.response.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {
    @Inject
    lateinit var saveLocal: SaveLocal
    val result: LiveData<Response<User>>
        get() = _login
    private val _login = MutableLiveData<Response<User>>()
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { response ->
                if (response.isSuccessful) {
                    val user = auth.currentUser?.let {
                        User(
                            email = it.email!!,
                            "abc",
                            it.uid
                        )
                    }
                    // Sign in success, update UI with the signed-in user's information

                    _login.value = Response.Success(user)
                    user?.let { saveLocal.saveUserLogin(it) }
                } else {
                    _login.value = response.exception?.let { Response.Error(it) }
                }
            }
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { response ->
                run {
                    if (response.isSuccessful) {

                    } else {
                        Log.w("error", "createUserWithEmail:failure", response.exception)
                    }
                }
            }
    }
}
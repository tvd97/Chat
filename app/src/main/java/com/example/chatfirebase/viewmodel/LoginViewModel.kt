package com.example.chatfirebase.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatfirebase.data.local.SaveLocal
import com.example.chatfirebase.model.User
import com.example.chatfirebase.model.response.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    database: FirebaseDatabase
) : ViewModel() {
    private var reference: DatabaseReference = database.getReference("user")

    @Inject
    lateinit var saveLocal: SaveLocal
    private var key = -1
    val result: LiveData<Response<User>>
        get() = _login
    private val _login = MutableLiveData<Response<User>>()
    val registerResponse: LiveData<Response<User>>
        get() = _registerResponse
    private val _registerResponse = MutableLiveData<Response<User>>()
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

    fun createAccount(email: String, password: String, name: String) {
        getLastKey()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { response ->
                if (response.isSuccessful) {
                    val user = auth.currentUser?.let { User(email, name = name, uid = it.uid) }
                    reference.child(key.toString()).apply {
                        child("email").setValue(email)
                        child("name").setValue(name)
                        child("uid").setValue(user!!.uid)

                    }
                    saveLocal.saveUserLogin(user!!)
                    _registerResponse.value = Response.Success(user)

                } else {
                    _registerResponse.value = Response.Error(response.exception!!)
                }
            }
    }

    private fun getLastKey() {
        reference.orderByKey().limitToLast(1).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                key = snapshot.children.last().key.toString().toInt() + 1
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
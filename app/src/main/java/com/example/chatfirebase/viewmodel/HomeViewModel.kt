package com.example.chatfirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatfirebase.model.User
import com.example.chatfirebase.model.response.Response
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(database: FirebaseDatabase) : ViewModel() {
    private var reference: DatabaseReference = database.getReference("user")
    val users: LiveData<Response<List<User>>>
        get() = _users
    private val _users = MutableLiveData<Response<List<User>>>()
    fun getListUser() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value: List<User> = Gson().fromJson(
                    Gson().toJson(snapshot.value),
                    object : TypeToken<List<User>>() {}.type
                )
                _users.value = Response.Success(value)
            }

            override fun onCancelled(error: DatabaseError) {
                _users.value = Response.Error(error)
            }

        })
    }
}
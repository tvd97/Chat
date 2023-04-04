package com.example.chatfirebase.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.chatfirebase.model.User
import com.google.gson.Gson
import javax.inject.Inject

class SaveLocal @Inject constructor(private val application: Application) {
    private val user = "USER"
    private val prefs: SharedPreferences =
        application.applicationContext.getSharedPreferences(
            "com.example.chatfirebase",
            Context.MODE_PRIVATE
        )!!

    fun saveUserLogin(u: User) {
        val str = Gson().toJson(u).toString()
        prefs.edit().putString(user, str).apply()
    }

    fun getUserLogin(): User? = Gson().fromJson(prefs.getString(user, null), User::class.java)


}
package com.example.chatfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatfirebase.databinding.ActivityMainBinding
import com.example.chatfirebase.data.local.SaveLocal
import com.example.chatfirebase.model.User
import com.example.chatfirebase.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var saveLocal: SaveLocal
    // private  lateinit var authListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        authListener = object :FirebaseAuth.AuthStateListener {
//            override fun onAuthStateChanged(p0: FirebaseAuth) {
//                TODO("Not yet implemented")
//            }
//        }
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.login!!)
        }
        binding.btnRegister.setOnClickListener {
            val user = saveLocal.getUserLogin()
//            if (!(binding.editEmail.text.isNullOrEmpty() && binding.editPassword.text.isNullOrEmpty()))
//                createAccount(
//                    binding.editEmail.text.toString(),
//                    binding.editPassword.text.toString()
//                )
            Log.e("dddd", "$user")
        }
        viewModel.result.observe(this) {

            if (it.isSuccess!!) {
                redirectChat()
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            // reload()
//        }
    }

    private fun reload() {
        TODO("Not yet implemented")
    }

    private fun redirectChat() {
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
    }
}
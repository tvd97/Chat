package com.example.chatfirebase.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatfirebase.databinding.ActivityMainBinding
import com.example.chatfirebase.data.local.SaveLocal
import com.example.chatfirebase.viewmodel.LoginViewModel
import androidx.activity.viewModels
import com.example.chatfirebase.model.response.Response
import com.example.chatfirebase.ui.BaseActivity
import com.example.chatfirebase.ui.home.HomeActivity
import com.example.chatfirebase.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = local.getUserLogin()
        user.let {
            viewModel.login(user!!.email, "yeuem0097")
        }
        viewModel.result.observe(this) {
            val intent = if (it is Response.Success) {
                Intent(this, HomeActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}
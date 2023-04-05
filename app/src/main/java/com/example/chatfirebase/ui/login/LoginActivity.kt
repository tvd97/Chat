package com.example.chatfirebase.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.chatfirebase.databinding.ActivityLoginBinding
import com.example.chatfirebase.ui.fragment.LoginFragment
import com.example.chatfirebase.ui.fragment.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(binding.activittyView!!.id, LoginFragment().apply {
                redirectRegister {
                    addRegisterView()
                }
            })
            addToBackStack("LoginFragment")
        }
    }

    private fun addRegisterView() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(binding.activittyView!!.id, RegisterFragment())
            addToBackStack("RegisterFragment")
        }
    }

}

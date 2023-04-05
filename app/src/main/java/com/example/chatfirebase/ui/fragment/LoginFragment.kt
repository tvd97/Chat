package com.example.chatfirebase.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.chatfirebase.databinding.FragmentLoginBinding
import com.example.chatfirebase.model.response.Response
import com.example.chatfirebase.ui.home.HomeActivity
import com.example.chatfirebase.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding
    private var registerCallback: (() -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setupEvent()
        onchangeData()
        return binding.root
    }

    private fun setupEvent() {
        binding.apply {
            btnRegister.setOnClickListener {
                registerCallback?.invoke()
            }
            btnLogin.setOnClickListener {
                viewModel.login(editEmail.text.toString(), editPassword.text.toString())
            }
        }

    }

    private fun onchangeData() {
        viewModel.result.observe(viewLifecycleOwner)
        {
            if (it is Response.Success) {
                val intent = Intent(activity, HomeActivity::class.java)
                activity?.startActivity(intent)
            }
        }
    }

    fun redirectRegister(register: (() -> Unit)?) {
        registerCallback = register
    }
}
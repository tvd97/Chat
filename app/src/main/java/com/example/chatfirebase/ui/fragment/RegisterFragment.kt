package com.example.chatfirebase.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.chatfirebase.databinding.FragmentRegisterBinding
import com.example.chatfirebase.model.response.Response
import com.example.chatfirebase.ui.home.HomeActivity
import com.example.chatfirebase.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        overrideOnBackPressed()
        setupEvent()
        onChangeData()
        return binding.root
    }

    private fun overrideOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            isEnabled = false
            activity?.onBackPressed()
        }
    }

    private fun setupEvent() {
        binding.apply {
            btnRegister.setOnClickListener {
                viewModel.createAccount(
                    email = editEmail.text.toString(),
                    name = editName.text.toString(),
                    password = editPassword.text.toString()
                )
            }
        }
    }

    private fun onChangeData() {
        viewModel.registerResponse.observe(viewLifecycleOwner)
        {
            if (it is Response.Success) {
                val intent = Intent(activity, HomeActivity::class.java)
                activity?.startActivity(intent)
            } else if (it is Response.Error) {
                Toast.makeText(context, "${it.exception}", Toast.LENGTH_LONG)
            }
        }
    }


}
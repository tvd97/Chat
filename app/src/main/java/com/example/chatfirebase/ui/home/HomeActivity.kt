package com.example.chatfirebase.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatfirebase.R
import com.example.chatfirebase.databinding.ActivityHomeBinding
import com.example.chatfirebase.model.response.Response
import com.example.chatfirebase.ui.BaseActivity
import com.example.chatfirebase.ui.activity.ChatActivity
import com.example.chatfirebase.ui.adapter.UserAdapter
import com.example.chatfirebase.ui.login.LoginActivity
import com.example.chatfirebase.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var adapter: UserAdapter
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
        setContentView(binding.root)
        viewModel.getListUser()
        setRecyclerview()
        viewModel.users.observe(this)
        {
            if (it is Response.Success) {
                adapter.submitData(it.data!!)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout)
            true.also {
                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
                finish()
            }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerview() {
        binding.rcvListUser.apply {
            adapter = this@HomeActivity.adapter
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
        }
        adapter.onClickItem = {
            val intent = Intent(this, ChatActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("user", it)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }
    }
}
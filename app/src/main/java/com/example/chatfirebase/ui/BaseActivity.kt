package com.example.chatfirebase.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.chatfirebase.data.local.SaveLocal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var local: SaveLocal
}
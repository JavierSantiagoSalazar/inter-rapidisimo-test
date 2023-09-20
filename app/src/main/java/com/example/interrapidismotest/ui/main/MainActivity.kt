package com.example.interrapidismotest.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interrapidismotest.R
import com.example.interrapidismotest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
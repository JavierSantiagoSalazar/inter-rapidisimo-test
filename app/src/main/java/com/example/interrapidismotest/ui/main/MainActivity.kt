package com.example.interrapidismotest.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interrapidismotest.R
import com.example.interrapidismotest.data.datasource.TableRemoteDataSource
import com.example.interrapidismotest.data.repository.TableRepository
import com.example.interrapidismotest.dataapp.server.TableRemoteDataSourceImpl
import com.example.interrapidismotest.databinding.ActivityMainBinding
import com.example.interrapidismotest.usecases.tables.RequestTablesUseCase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
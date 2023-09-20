package com.example.interrapidismotest

import android.app.Application
import androidx.room.Room
import com.example.interrapidismotest.data.database.TableDatabase

class App : Application() {

    lateinit var db: TableDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            TableDatabase::class.java, "table-db"
        ).build()
    }
}

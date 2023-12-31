package com.example.interrapidismotest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Table::class], version = 1, exportSchema = false)
abstract class TableDatabase : RoomDatabase() {

    abstract fun tableDao(): TableDao
}

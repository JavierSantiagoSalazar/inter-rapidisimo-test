package com.example.interrapidismotest.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Table(
    val tableName: String,
    @PrimaryKey val primaryKey: String,
    val queryCreation: String,
    val batchSize: Int,
    val filter: String,
    val error: String?,
    val fieldsNumber: Int,
    val appMethod: String?,
    val dateUpdatesSync: String
)

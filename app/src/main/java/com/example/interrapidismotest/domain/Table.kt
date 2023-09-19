package com.example.interrapidismotest.domain

data class Table(
    val tableName: String,
    val primaryKey: String,
    val queryCreation: String,
    val batchSize: Int,
    val filter: String,
    val error: String?,
    val fieldsNumber: Int,
    val appMethod: String?,
    val dateUpdatesSync: String
)

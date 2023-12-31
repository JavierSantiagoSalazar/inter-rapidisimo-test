package com.example.interrapidismotest.data.datasource

import com.example.interrapidismotest.domain.Table
import kotlinx.coroutines.flow.Flow
import com.example.interrapidismotest.domain.Error

interface TableLocalDataSource {
    val tables: Flow<List<Table>>

    suspend fun isEmpty(): Boolean

    suspend fun save(tables: List<Table>): Error?
}

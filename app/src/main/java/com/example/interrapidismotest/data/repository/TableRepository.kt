package com.example.interrapidismotest.data.repository

import com.example.interrapidismotest.data.datasource.TableLocalDataSource
import com.example.interrapidismotest.data.datasource.TableRemoteDataSource
import com.example.interrapidismotest.domain.Error
import com.example.interrapidismotest.domain.Table
import kotlinx.coroutines.flow.Flow

class TableRepository(
    private val localDataSource: TableLocalDataSource,
    private val remoteDataSource: TableRemoteDataSource
) {

    val tables get() = localDataSource.tables

    fun findTableByName(tableName: String): Flow<Table> = localDataSource.findTableByName(tableName)

    suspend fun requestTablesData(): Error? {
        if (localDataSource.isEmpty()) {
            val tables = remoteDataSource.getTablesDataFromServer()
            tables.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }
}

package com.example.interrapidismotest.data.repository

import com.example.interrapidismotest.data.datasource.TableLocalDataSource
import com.example.interrapidismotest.data.datasource.TableRemoteDataSource
import com.example.interrapidismotest.domain.Error
import javax.inject.Inject

class TableRepository @Inject constructor(
    private val localDataSource: TableLocalDataSource,
    private val remoteDataSource: TableRemoteDataSource
) {

    val tables get() = localDataSource.tables

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

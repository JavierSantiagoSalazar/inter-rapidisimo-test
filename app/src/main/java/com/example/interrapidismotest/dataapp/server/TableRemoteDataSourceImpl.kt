package com.example.interrapidismotest.dataapp.server

import arrow.core.Either
import com.example.interrapidismotest.data.datasource.TableRemoteDataSource
import com.example.interrapidismotest.dataapp.tryCall
import com.example.interrapidismotest.di.annotations.User
import com.example.interrapidismotest.domain.Error
import com.example.interrapidismotest.domain.Table

class TableRemoteDataSourceImpl(
    @User private val user: String,
) : TableRemoteDataSource {

    override suspend fun getTablesDataFromServer(): Either<Error, List<Table>> = tryCall {
        RemoteConnection.service
            .getTablesData(user)
            .toDomainModel()
    }
}

private fun List<RemoteTable>.toDomainModel(): List<Table> =
    map { it.toDomainModel() }

private fun RemoteTable.toDomainModel(): Table =
    Table(
        tableName = tableName,
        primaryKey = primaryKey,
        queryCreation = queryCreation,
        batchSize = batchSize,
        filter = filter,
        error = error,
        fieldsNumber = fieldsNumber,
        appMethod = appMethod,
        dateUpdatesSync = dateUpdatesSync
    )

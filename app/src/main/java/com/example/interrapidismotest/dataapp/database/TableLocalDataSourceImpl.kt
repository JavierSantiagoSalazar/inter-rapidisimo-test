package com.example.interrapidismotest.dataapp.database

import com.example.interrapidismotest.data.datasource.TableLocalDataSource
import com.example.interrapidismotest.dataapp.tryCall
import com.example.interrapidismotest.domain.Error
import com.example.interrapidismotest.domain.Table
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.interrapidismotest.dataapp.database.Table as DbTable

class TableLocalDataSourceImpl(
    private val tableDao: TableDao,
) : TableLocalDataSource {

    override val tables: Flow<List<Table>> = tableDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = tableDao.tableCount() == 0

    override suspend fun save(tables: List<Table>): Error? =
        tryCall {
            tableDao.insertTables(tables.fromDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override fun findTableByName(tableName: String): Flow<Table> =
        tableDao.findByName(tableName).map { it.toDomainModel() }

}

private fun List<DbTable>.toDomainModel(): List<Table> =
    map { it.toDomainModel() }

private fun DbTable.toDomainModel(): Table =
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

private fun List<Table>.fromDomainModel(): List<DbTable> =
    map { it.fromDomainModel() }

private fun Table.fromDomainModel(): DbTable =
    DbTable(
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

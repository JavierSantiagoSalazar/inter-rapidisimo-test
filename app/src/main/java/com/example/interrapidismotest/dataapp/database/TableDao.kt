package com.example.interrapidismotest.dataapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {

    @Query("SELECT * FROM `Table`")
    fun getAll(): Flow<List<Table>>

    @Query("SELECT * FROM `Table` WHERE tableName = :tableName")
    fun findByName(tableName: String): Flow<Table>

    @Query("SELECT COUNT(tableName) FROM `Table`")
    suspend fun tableCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTables(tables: List<Table>)
}

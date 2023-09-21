package com.example.interrapidismotest.data.repository

import arrow.core.right
import com.example.interrapidismotest.data.datasource.TableLocalDataSource
import com.example.interrapidismotest.data.datasource.TableRemoteDataSource
import com.example.interrapidismotest.testshared.sampleTable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TableRepositoryTest {

    @Mock
    lateinit var localDataSource: TableLocalDataSource

    @Mock
    lateinit var remoteDataSource: TableRemoteDataSource

    private lateinit var tableRepository: TableRepository

    @Before
    fun setup(){
        tableRepository = TableRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `Tables are taken from local data source if is available`(): Unit = runTest {

        val localTables = flowOf(listOf(sampleTable.copy("TableName1")))
        whenever(localDataSource.tables).thenReturn(localTables)

        val result = tableRepository.tables

        assertEquals(localTables, result)
    }

    @Test
    fun `Tables are saved to local data source when it's empty`(): Unit = runTest {
        val remoteTables = listOf(sampleTable.copy("TableName1"))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(remoteDataSource.getTablesDataFromServer()).thenReturn(remoteTables.right())

        tableRepository.requestTablesData()

        verify(localDataSource).save(remoteTables)
    }
}

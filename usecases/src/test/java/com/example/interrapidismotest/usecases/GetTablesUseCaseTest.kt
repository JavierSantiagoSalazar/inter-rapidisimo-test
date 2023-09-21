package com.example.interrapidismotest.usecases

import com.example.interrapidismotest.data.repository.TableRepository
import com.example.interrapidismotest.testshared.sampleTable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetTablesUseCaseTest {

    @Mock
    private lateinit var tablesRepository: TableRepository

    private lateinit var getTablesUseCase: GetTablesUseCase

    @Before
    fun setup() {
        getTablesUseCase = GetTablesUseCase(tablesRepository)
    }

    @Test
    fun `when GetTablesUseCase is called should returns tables`(): Unit = runTest {

            val tables =
                flowOf(listOf(sampleTable.copy("TableName1")))
            whenever(tablesRepository.tables).thenReturn(tables)


            val result = getTablesUseCase()

            assertEquals(tables, result)
        }
}

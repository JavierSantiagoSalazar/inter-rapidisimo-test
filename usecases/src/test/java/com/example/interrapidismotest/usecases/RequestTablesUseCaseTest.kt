package com.example.interrapidismotest.usecases

import com.example.interrapidismotest.data.repository.TableRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RequestTablesUseCaseTest{

    @Mock
    private lateinit var tableRepository: TableRepository

    private lateinit var requestTablesUseCase: RequestTablesUseCase

    @Before
    fun setup() {
        requestTablesUseCase = RequestTablesUseCase(tableRepository)
    }

    @Test
    fun `when invoke calls should call tableRepository`(): Unit = runTest {

            requestTablesUseCase()

            verify(tableRepository).requestTablesData()
        }
}

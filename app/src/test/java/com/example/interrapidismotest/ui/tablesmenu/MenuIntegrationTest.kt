package com.example.interrapidismotest.ui.tablesmenu

import app.cash.turbine.test
import com.example.interrapidismotest.buildDatabaseTables
import com.example.interrapidismotest.buildRemoteTables
import com.example.interrapidismotest.buildTableRepositoryWith
import com.example.interrapidismotest.data.server.RemoteTable
import com.example.interrapidismotest.testrules.CoroutinesTestRule
import com.example.interrapidismotest.ui.tablesmenu.MenuViewModel.UiState
import com.example.interrapidismotest.usecases.GetTablesUseCase
import com.example.interrapidismotest.usecases.RequestTablesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import com.example.interrapidismotest.data.database.Table as DatabaseTable

@ExperimentalCoroutinesApi
class MenuIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteTablesData = buildRemoteTables("TableName1", "TableName2", "TableName3")
        val vm = buildViewModelWith(tablesRemoteData = remoteTablesData)

        vm.getTables()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(tables = emptyList()), awaitItem())
            assertEquals(
                UiState(tables = emptyList(), loading = true),
                awaitItem()
            )
            assertEquals(
                UiState(tables = emptyList(), loading = false),
                awaitItem()
            )

            val tables = awaitItem().tables
            assertEquals("TableName1", tables?.get(0)?.tableName ?: "")
            assertEquals("TableName2", tables?.get(1)?.tableName ?: "")
            assertEquals("TableName3", tables?.get(2)?.tableName ?: "")
            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localTablesData = buildDatabaseTables("TableName1", "TableName2", "TableName3")
        val remoteTablesData = buildRemoteTables("TableName1", "TableName2", "TableName3")
        val vm = buildViewModelWith(
            tablesLocalData = localTablesData,
            tablesRemoteData = remoteTablesData,
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())

            val tables = awaitItem().tables
            assertEquals("TableName1", tables?.get(0)?.tableName ?: "")
            assertEquals("TableName2", tables?.get(1)?.tableName ?: "")
            assertEquals("TableName3", tables?.get(2)?.tableName ?: "")
            cancel()
        }
    }

    private fun buildViewModelWith(
        tablesLocalData: List<DatabaseTable> = emptyList(),
        tablesRemoteData: List<RemoteTable> = emptyList(),
    ): MenuViewModel {
        val tablesRepository = buildTableRepositoryWith(tablesLocalData, tablesRemoteData)
        val getTablesUseCase = GetTablesUseCase(tablesRepository)
        val requestTablesUseCase = RequestTablesUseCase(tablesRepository)
        return MenuViewModel(
            getTablesUseCase = getTablesUseCase,
            requestTablesUseCase = requestTablesUseCase
        )
    }
}

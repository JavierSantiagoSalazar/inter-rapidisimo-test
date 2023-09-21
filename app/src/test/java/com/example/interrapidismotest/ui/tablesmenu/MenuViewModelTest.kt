package com.example.interrapidismotest.ui.tablesmenu

import app.cash.turbine.test
import com.example.interrapidismotest.testrules.CoroutinesTestRule
import com.example.interrapidismotest.testshared.sampleTable
import com.example.interrapidismotest.ui.tablesmenu.MenuViewModel.*
import com.example.interrapidismotest.usecases.GetTablesUseCase
import com.example.interrapidismotest.usecases.RequestTablesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MenuViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getTablesUseCase: GetTablesUseCase

    @Mock
    lateinit var requestTablesUseCase: RequestTablesUseCase

    private lateinit var vm: MenuViewModel

    private val tables = listOf(sampleTable.copy("TableName1"))

    @Before
    fun setup() {
        whenever(getTablesUseCase()).thenReturn(flowOf(tables))
        vm = MenuViewModel(getTablesUseCase, requestTablesUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(tables = tables), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when GetTablesUseCase is called and hidden when it finishes requesting tables`() =
        runTest {
            vm.getTables()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(tables = tables), awaitItem())
                assertEquals(UiState(tables = tables, loading = true),
                    awaitItem())
                assertEquals(UiState(tables = tables, loading = false),
                    awaitItem())
                cancel()
            }
        }

    @Test
    fun `Tables are requested when GetTablesUseCase starts`() = runTest {
        vm.getTables()
        runCurrent()

        verify(requestTablesUseCase).invoke()
    }
}

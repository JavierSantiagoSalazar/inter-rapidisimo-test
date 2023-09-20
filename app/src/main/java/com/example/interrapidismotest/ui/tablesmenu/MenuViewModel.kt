package com.example.interrapidismotest.ui.tablesmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.interrapidismotest.dataapp.toError
import com.example.interrapidismotest.domain.Error
import com.example.interrapidismotest.domain.Table
import com.example.interrapidismotest.usecases.tables.GetTablesUseCase
import com.example.interrapidismotest.usecases.tables.RequestTablesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    getTablesUseCase: GetTablesUseCase,
    private val requestTablesUseCase: RequestTablesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getTablesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { tables -> _state.update { it.copy(tables = tables) } }
        }
    }

    fun getTables() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestTablesUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val tables: List<Table>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MenuViewModelFactory(
    private val getTablesUseCase: GetTablesUseCase,
    private val requestTablesUseCase: RequestTablesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MenuViewModel(getTablesUseCase, requestTablesUseCase) as T
    }
}

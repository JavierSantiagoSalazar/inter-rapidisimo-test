package com.example.interrapidismotest.usecases.tables

import com.example.interrapidismotest.data.repository.TableRepository
import com.example.interrapidismotest.domain.Table
import kotlinx.coroutines.flow.Flow

class GetTablesUseCase (private val tableRepository: TableRepository) {

    operator fun invoke(): Flow<List<Table>> {
        return tableRepository.tables
    }
}

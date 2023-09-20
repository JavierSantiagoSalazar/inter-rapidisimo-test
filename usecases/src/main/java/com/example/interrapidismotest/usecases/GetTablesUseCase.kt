package com.example.interrapidismotest.usecases

import com.example.interrapidismotest.data.repository.TableRepository
import com.example.interrapidismotest.domain.Table
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTablesUseCase @Inject constructor(private val tableRepository: TableRepository) {

    operator fun invoke(): Flow<List<Table>> {
        return tableRepository.tables
    }
}

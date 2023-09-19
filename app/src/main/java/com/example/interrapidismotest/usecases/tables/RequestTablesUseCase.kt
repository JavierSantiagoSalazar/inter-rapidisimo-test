package com.example.interrapidismotest.usecases.tables

import com.example.interrapidismotest.data.repository.TableRepository
import com.example.interrapidismotest.domain.Error

class RequestTablesUseCase (private val tableRepository: TableRepository) {

    suspend operator fun invoke(): Error? {
        return tableRepository.requestTablesData()
    }
}

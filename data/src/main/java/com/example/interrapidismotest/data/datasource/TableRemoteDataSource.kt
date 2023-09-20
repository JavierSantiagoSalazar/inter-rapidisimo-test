package com.example.interrapidismotest.data.datasource

import arrow.core.Either
import com.example.interrapidismotest.domain.Error
import com.example.interrapidismotest.domain.Table

interface TableRemoteDataSource {

    suspend fun getTablesDataFromServer(): Either<Error, List<Table>>
}

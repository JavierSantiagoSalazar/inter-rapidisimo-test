package com.example.interrapidismotest.di

import com.example.interrapidismotest.data.database.TableLocalDataSourceImpl
import com.example.interrapidismotest.data.datasource.TableLocalDataSource
import com.example.interrapidismotest.data.datasource.TableRemoteDataSource
import com.example.interrapidismotest.data.server.TableRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindTableRemoteDataSource(tableRemoteDataSourceImpl: TableRemoteDataSourceImpl): TableRemoteDataSource

    @Binds
    abstract fun bindTableLocalDataSource(tableRemoteLocalSourceImpl: TableLocalDataSourceImpl): TableLocalDataSource

}

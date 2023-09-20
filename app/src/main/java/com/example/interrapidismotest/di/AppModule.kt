package com.example.interrapidismotest.di

import android.app.Application
import androidx.room.Room
import com.example.interrapidismotest.data.database.TableDao
import com.example.interrapidismotest.data.database.TableDatabase
import com.example.interrapidismotest.data.server.TableRemoteService
import com.example.interrapidismotest.di.annotations.ApiUrl
import com.example.interrapidismotest.di.annotations.User
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        TableDatabase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideDao(db: TableDatabase): TableDao = db.tableDao()

    @Provides
    @Singleton
    @User
    fun provideUser(): String =
        "admin"

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String =
        "https://apitesting.interrapidisimo.co/FtAppAgencias012/apiControllerPruebas/api/SincronizadorDatos/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Reusable
    fun provideTableService(retrofit: Retrofit): TableRemoteService {
        return retrofit.create(TableRemoteService::class.java)
    }
}

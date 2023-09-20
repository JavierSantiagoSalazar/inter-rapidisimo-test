package com.example.interrapidismotest.data.server

import retrofit2.http.GET
import retrofit2.http.Header

interface TableRemoteService {

    @GET("ObtenerEsquema/true/")
    suspend fun getTablesData(
        @Header("usuario") region: String
    ): List<RemoteTable>
}

package com.example.interrapidismotest.data.server

import com.google.gson.annotations.SerializedName

data class RemoteTable(
    @SerializedName("NombreTabla") val tableName: String,
    @SerializedName("Pk") val primaryKey: String,
    @SerializedName("QueryCreacion") val queryCreation: String,
    @SerializedName("BatchSize") val batchSize: Int,
    @SerializedName("Filtro") val filter: String,
    @SerializedName("Error") val error: String?,
    @SerializedName("NumeroCampos") val fieldsNumber: Int,
    @SerializedName("MetodoApp") val appMethod: String?,
    @SerializedName("FechaActualizacionSincro") val dateUpdatesSync: String
)

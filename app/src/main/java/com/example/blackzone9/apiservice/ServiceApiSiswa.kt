package com.example.blackzone9.apiservice

import com.example.blackzone9.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface ServiceApiSiswa {@GET("coba.php")
suspend fun getSiswa(): List<DataSiswa>

    @POST("insert.php")
    suspend fun postDataSiswa(@Body dataSiswa: DataSiswa): retrofit2.Response<DataSiswa>

    @GET("coba.php")
    suspend fun getStatusSiswa(@Query("id") id: Int): Response<List<DataSiswa>>

    @PUT("coba1.php")
    suspend fun editDataSiswa(@Body dataSiswa: DataSiswa): retrofit2.Response<DataSiswa>

    @DELETE("delete.php")
    suspend fun deleteDataSiswa(@Query("id") id: Int): retrofit2.Response<DataSiswa>



}
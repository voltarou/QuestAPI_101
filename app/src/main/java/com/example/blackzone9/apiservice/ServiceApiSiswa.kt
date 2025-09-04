package com.example.blackzone9.apiservice

import com.example.blackzone9.modeldata.DataSiswa
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



interface ServiceApiSiswa {@GET("coba.php")
suspend fun getSiswa(): List<DataSiswa>

    @POST("insert.php")
    suspend fun postDataSiswa(@Body dataSiswa: DataSiswa): retrofit2.Response<DataSiswa>

}
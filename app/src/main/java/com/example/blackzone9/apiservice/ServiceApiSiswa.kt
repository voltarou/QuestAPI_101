package com.example.blackzone9.apiservice

import com.example.blackzone9.modeldata.DataSiswa
import retrofit2.http.GET

interface ServiceApiSiswa {@GET("coba.php")
suspend fun getSiswa(): List<DataSiswa>
}
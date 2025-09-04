package com.example.blackzone9.repositori

import com.example.blackzone9.apiservice.ServiceApiSiswa
import com.example.blackzone9.modeldata.DataSiswa

interface RepositoriDataSiswa {
    suspend fun getSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa) : retrofit2.Response<DataSiswa>

}

class JaringanRepositoriDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa

): RepositoriDataSiswa {
    override suspend fun getSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa): retrofit2.Response<DataSiswa> = serviceApiSiswa.postDataSiswa(dataSiswa)



}
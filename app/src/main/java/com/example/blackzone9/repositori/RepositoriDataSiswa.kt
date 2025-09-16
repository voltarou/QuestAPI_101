package com.example.blackzone9.repositori

import com.example.blackzone9.apiservice.ServiceApiSiswa
import com.example.blackzone9.modeldata.DataSiswa
import retrofit2.Response

interface RepositoriDataSiswa {
    suspend fun getSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa) : Response<DataSiswa>
    suspend fun getSatuSiswa(id: Int) : Response<DataSiswa> // Corrected name
    suspend fun editDataSiswa(dataSiswa: DataSiswa) : Response<DataSiswa>
    suspend fun editSatuSiswa(id: Int, dataSiswa: DataSiswa) : Response<DataSiswa> // Corrected name
    suspend fun hapusSatuSiswa(id: Int) : Response<DataSiswa> // Corrected name and return type
}

class JaringanRepositoriDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoriDataSiswa {
    override suspend fun getSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa): Response<DataSiswa> = serviceApiSiswa.postDataSiswa(dataSiswa)
    override suspend fun getSatuSiswa(id: Int): Response<DataSiswa> {
        val response = serviceApiSiswa.getStatusSiswa(id)
        return if (response.isSuccessful) {
            val body = response.body()?.firstOrNull()
            Response.success(body)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
    override suspend fun editDataSiswa(dataSiswa: DataSiswa): Response<DataSiswa> = serviceApiSiswa.editDataSiswa(dataSiswa)
    override suspend fun hapusSatuSiswa(id: Int): Response<DataSiswa> = serviceApiSiswa.deleteDataSiswa(id) // Implement with old name and a new return type
    override suspend fun editSatuSiswa(id: Int, dataSiswa: DataSiswa): Response<DataSiswa> {
        val response = serviceApiSiswa.getStatusSiswa(id)
        return if (response.isSuccessful) {
            val body = response.body()?.firstOrNull()
            if (body != null) {
                val updatedDataSiswa = body.copy(
                    nama = dataSiswa.nama,
                    alamat = dataSiswa.alamat,
                    telpon = dataSiswa.telpon
                )
                serviceApiSiswa.editDataSiswa(updatedDataSiswa)
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } else {
            Response.error(response.code(), response.errorBody()!!)
            }

    }
}
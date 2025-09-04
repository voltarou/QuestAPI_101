package com.example.blackzone9.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.blackzone9.modeldata.DataSiswa
import com.example.blackzone9.modeldata.DetailSiswa
import com.example.blackzone9.modeldata.UIStateSiswa

import com.example.blackzone9.modeldata.toSiswa
import com.example.blackzone9.repositori.RepositoriDataSiswa

import retrofit2.Response




class EntryViewModel (private val repositoriDataSiswa: RepositoriDataSiswa): ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }





    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    suspend fun addSiswa() {
        if (validasiInput()) {
            val sip: Response<DataSiswa> =
                repositoriDataSiswa.postDataSiswa(uiStateSiswa.detailSiswa.toSiswa())
            if (sip.isSuccessful) {
                print("sukses" + sip.message())
            } else {
                print("gagal" + sip.errorBody())
            }
        }
    }





}





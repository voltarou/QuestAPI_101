package com.example.blackzone9.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackzone9.modeldata.DataSiswa
import com.example.blackzone9.modeldata.DetailSiswa
import com.example.blackzone9.repositori.RepositoriDataSiswa
import com.example.blackzone9.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.blackzone9.modeldata.UIStateSiswa
import com.example.blackzone9.modeldata.toSiswa


class EditViewModel(savedStateHandle: SavedStateHandle, private val repositoryDataSiswa: RepositoriDataSiswa): ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    init {
        viewModelScope.launch {
            uiStateSiswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                .toUiStateSiswa(true)
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa ): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    suspend fun editSatuSiswa() {
        if (validasiInput(uiStateSiswa.detailSiswa)) {
            val response: Response<DataSiswa> = repositoryDataSiswa.editSatuSiswa(idSiswa, uiStateSiswa.detailSiswa.toSiswa())

            if (response.isSuccessful) {
                println("Update Sukses : ${response.message()}")
            } else {
                println("Update Error : ${response.errorBody()?.string()}")
            }
        }
    }
}

// Tambahkan fungsi ekstensi ini di luar kelas ViewModel
fun Response<DataSiswa>.toUiStateSiswa(isEntryValid: Boolean): UIStateSiswa {
    val siswa = this.body()
    return if (siswa != null) {
        UIStateSiswa(
            detailSiswa = DetailSiswa(
                id = siswa.id,
                nama = siswa.nama,
                alamat = siswa.alamat,
                telpon = siswa.telpon
            ),
            isEntryValid = isEntryValid
        )
    } else {
        // Mengembalikan UIStateSiswa kosong atau dengan status error jika body null
        UIStateSiswa()
    }
}
package com.example.blackzone9.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackzone9.modeldata.DataSiswa
import com.example.blackzone9.repositori.RepositoriDataSiswa
import com.example.blackzone9.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

// Sealed interface untuk merepresentasikan status UI
sealed interface StatusUiDetail {
    object Loading : StatusUiDetail
    data class Success(val satusiswa: Response<DataSiswa>) : StatusUiDetail
    object Error : StatusUiDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriDataSiswa
) : ViewModel() {

    private val siswaId: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    // Menggunakan StateFlow untuk mengobservasi perubahan status UI
    private val _statusUiDetail = MutableStateFlow<StatusUiDetail>(StatusUiDetail.Loading)
    val statusUiDetail: StateFlow<StatusUiDetail> = _statusUiDetail.asStateFlow()

    init {
        getSatuSiswa()
    }

    // Fungsi untuk mengambil data siswa dari repositori
    fun getSatuSiswa() {
        viewModelScope.launch {
            _statusUiDetail.value = StatusUiDetail.Loading
            _statusUiDetail.value = try {
                StatusUiDetail.Success(satusiswa = repositoriSiswa.getSatuSiswa(siswaId))
            } catch (e: HttpException) {
                StatusUiDetail.Error
            } catch (e: IOException) {
                StatusUiDetail.Error
            }
        }
    }

    // Fungsi untuk menghapus satu data siswa
    fun hapusSatuSiswa() {
        viewModelScope.launch {
            try {
                // Panggil fungsi hapus dari repositori
                val response = repositoriSiswa.hapusSatuSiswa(siswaId)
                if (response.isSuccessful) {
                    println("Data berhasil dihapus: ${response.message()}")
                } else {
                    println("Data gagal dihapus: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Terjadi kesalahan saat menghapus data: ${e.message}")
            }
        }
    }
}
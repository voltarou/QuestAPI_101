package com.example.blackzone9.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackzone9.modeldata.DataSiswa
import com.example.blackzone9.repositori.RepositoriDataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface StatusUiSiswa {
    data class Success(val siswa: List<DataSiswa> = listOf()) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel(private val repositoriDataSiswa: RepositoriDataSiswa) : ViewModel() {
    /**
     * The mutable State that stores the status of the most recent request
     */
    var statusUiSiswa: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
        private set

    /**
     * Call getSiswa() on init to get Siswa data
     */
    init {
        getSiswa()
    }

    /**
     * Gets Siswa data from the RepositoriDataSiswa API and updates the
     * [DataSiswa] [List] [statusUiSiswa].
     */
    fun getSiswa() {
        viewModelScope.launch {
            statusUiSiswa = StatusUiSiswa.Loading
            statusUiSiswa = try {
                StatusUiSiswa.Success(repositoriDataSiswa.getSiswa())
            } catch (e: IOException) {
                StatusUiSiswa.Error
            } catch (e: HttpException) {
                StatusUiSiswa.Error
            }
        }
    }
}

package com.example.blackzone9.modeldata

import kotlinx.serialization.Serializable


@Serializable
data class DataSiswa(
    val id: Int,
    val nama: String,
    val alamat: String,
    val telpon: String
)
package com.example.blackzone9.uicontroller.route

import com.example.blackzone9.R

object DestinasiDetail: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = R.string.entry_siswa
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"

}
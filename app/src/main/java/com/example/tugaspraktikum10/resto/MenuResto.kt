package com.example.tugaspraktikum10.resto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_table") //nama tabel
data class MenuResto(
    var nama: String, //membuat kolom nama
    var deskripsi: String, // membuat kolom deskripsi
    var rate: Int //membuat kolom rate
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 //primary key
}
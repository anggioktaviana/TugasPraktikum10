package com.example.tugaspraktikum10.resto

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MenuRestoDao {
    @Insert //memasukkan note ke db
    fun insert(note: MenuResto)
    @Update //update note
    fun update(note: MenuResto)
    @Delete //menghapus note
    fun delete(note: MenuResto)
    @Query("DELETE FROM menu_table")
    fun deleteAllNotes()
    @Query("SELECT * FROM menu_table ORDER BY rate DESC")
    fun getAllNotes(): LiveData<List<MenuResto>>
}
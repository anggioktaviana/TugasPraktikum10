package com.example.tugaspraktikum10.resto

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MenuRestoRepository(application: Application) {
    private var menurestoDao: MenuRestoDao
    private var allMenu: LiveData<List<MenuResto>>

    init {
        val database: MenuRestoDatabase = MenuRestoDatabase.getInstance(
            application.applicationContext
        )!!
        menurestoDao = database.menurestoDao()
        allMenu = menurestoDao.getAllNotes()
    }
    //menambahkan insert note dari noteDao ke repository
    fun insert(note: MenuResto) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(menurestoDao).execute(note)
    }
    //menambahkan update note dari noteDao ke repository
    fun update(note: MenuResto) {
        val updateNoteAsyncTask = UpdateNoteAsyncTask(menurestoDao).execute(note)
    }
    //menambahkan delete note dari noteDao ke repository
    fun delete(note: MenuResto) {
        val deleteNoteAsyncTask = DeleteNoteAsyncTask(menurestoDao).execute(note)
    }

    fun deleteAllMenus() {
        val deleteAllNotesAsyncTask = DeleteAllNotesAsyncTask(menurestoDao).execute()
    }

    fun getAllMenus(): LiveData<List<MenuResto>> {
        return allMenu
    }
    //eksekusi AsyncTask
    companion object {
        private class InsertNoteAsyncTask(menurestoDao: MenuRestoDao) : AsyncTask<MenuResto, Unit, Unit>() {
            val menurestoDao = menurestoDao
            override fun doInBackground(vararg p0: MenuResto?) {
                menurestoDao.insert(p0[0]!!)
            }
        }

        private class UpdateNoteAsyncTask(menurestoDao: MenuRestoDao) : AsyncTask<MenuResto, Unit, Unit>() {
            val menurestoDao = menurestoDao
            override fun doInBackground(vararg p0: MenuResto?) {
                menurestoDao.update(p0[0]!!)
            }
        }

        private class DeleteNoteAsyncTask(menurestoDao: MenuRestoDao) : AsyncTask<MenuResto, Unit, Unit>() {
            val menurestoDao = menurestoDao
            override fun doInBackground(vararg p0: MenuResto?) {
                menurestoDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllNotesAsyncTask(menurestoDao: MenuRestoDao) : AsyncTask<Unit, Unit, Unit>() {
            val menurestoDao = menurestoDao
            override fun doInBackground(vararg p0: Unit?) {
                menurestoDao.deleteAllNotes()
            }
        }
    }
}
package com.example.tugaspraktikum10.resto

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

//menganotasi kelas menjadi database ruangan dengan tabel (entitas) kelas Note
@Database(entities = [MenuResto::class], version = 1)
abstract class MenuRestoDatabase : RoomDatabase() {
    abstract fun menurestoDao(): MenuRestoDao
    companion object {
        private var instance: MenuRestoDatabase? = null
        fun getInstance(context: Context): MenuRestoDatabase? {
            if (instance == null) {
                synchronized(MenuRestoDatabase::class) {
                    instance = Room.databaseBuilder( //membuat database
                        context.applicationContext,
                        MenuRestoDatabase::class.java, "menu_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }
    class PopulateDbAsyncTask(db: MenuRestoDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.menurestoDao()
        override fun doInBackground(vararg p0: Unit?) {
            noteDao?.insert(MenuResto("Coba 1", "Deskripsi 1", 1))
        }
    }
}
package com.example.moviesearch.app

import android.app.Application
import androidx.room.Room
import com.example.moviesearch.room.HistoryDao
import com.example.moviesearch.room.HistoryDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        createDBAsync()
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null

        @Volatile
        private var isDbInitialized = false
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) {
                            throw IllegalStateException("Application is null while creating DataBase")
                        }
                        createDB()
                    }
                }
            }
            return db!!.historyDao()
        }

        private fun createDB() {
            if (isDbInitialized) return

            isDbInitialized = true
            db = Room.databaseBuilder(
                appInstance!!.applicationContext,
                HistoryDataBase::class.java,
                DB_NAME
            )
                .allowMainThreadQueries()
                .build()
        }

        private fun createDBAsync() {
            Thread { createDB() }.start()
        }
    }
}
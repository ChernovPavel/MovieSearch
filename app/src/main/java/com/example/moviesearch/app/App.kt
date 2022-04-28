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
        private lateinit var db: HistoryDataBase

        @Volatile
        private var isDbInitialized = false
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {
            return db.historyDao()
        }

        private fun createDBAsync() {
            if (isDbInitialized) return
            Thread {
                isDbInitialized = true
                db = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    HistoryDataBase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }.start()
        }
    }
}
package com.example.moviesearch.app

import android.app.Application
import androidx.room.Room
import com.example.moviesearch.di.DaggerAppComponent

import com.example.moviesearch.room.HistoryDao
import com.example.moviesearch.room.HistoryDataBase

class App : Application() {

    val appComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {

            synchronized(HistoryDataBase::class.java) {
                if (db == null) {
                    if (appInstance == null) throw IllegalAccessException("APP must not be null")
                    db = appInstance?.applicationContext?.let {
                        Room.databaseBuilder(
                            it,
                            HistoryDataBase::class.java,
                            DB_NAME
                        )
                            .build()
                    }
                }
            }
            return db!!.historyDao()
        }
    }
}
package com.example.moviesearch.viewmodel

import android.location.Geocoder
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.IOException

class MapsViewModel : ViewModel() {

    val textAddressLiveData: MutableLiveData<String> = MutableLiveData()
    private val handlerThread = HandlerThread("Thread1")

    fun getAddressForTextView(geoCoder: Geocoder, latitude: Double, longitude: Double) {
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.post {
            try {
                val addresses =
                    geoCoder.getFromLocation(latitude,
                        longitude, 1)
                textAddressLiveData.postValue(addresses[0].getAddressLine(0))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        handlerThread.quitSafely()
    }
}
package com.example.moviesearch.viewmodel

import android.location.Address
import android.location.Geocoder
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

class MapsViewModel : ViewModel() {

    val textAddressLiveData: MutableLiveData<String> = MutableLiveData()
    val searchByAddressLiveData: MutableLiveData<LatLng> = MutableLiveData()

    fun getAddressForTextView(geoCoder: Geocoder, latitude: Double, longitude: Double) {
        val handlerThread = HandlerThread("Thread1")
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

    fun searchByAddress(geoCoder: Geocoder, searchText: String) {
        val handlerThread = HandlerThread("Thread2")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.post {
            try {
                val addresses = geoCoder.getFromLocationName(searchText, 1)
                if (addresses.size > 0) {
                    goToAddress(addresses)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        handlerThread.quitSafely()
    }

    private fun goToAddress(
        addresses: MutableList<Address>,
    ) {
        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )
        searchByAddressLiveData.postValue(location)
    }
}
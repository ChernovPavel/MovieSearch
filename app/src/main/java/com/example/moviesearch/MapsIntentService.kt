package com.example.moviesearch

import android.app.IntentService
import android.content.Intent
import android.location.Geocoder
import com.example.moviesearch.view.map.BROADCAST_INTENT_FILTER
import com.example.moviesearch.view.map.MAPS_FRAGMENT_BROADCAST_EXTRA
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

const val MAP_SERVICE_EXTRA = "MapServiceExtra"

class MyIntentService : IntentService("MyIntentService") {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: MyIntentService
    }

    override fun onHandleIntent(intent: Intent?) {

        val geoCoder = Geocoder(applicationContext)
        val searchText = intent?.getStringExtra(MAP_SERVICE_EXTRA)

        try {
            val addresses = geoCoder.getFromLocationName(searchText, 1)

            if (addresses.size > 0) {
                val location = LatLng(addresses[0].latitude, addresses[0].longitude)
                sendMessageToBroadcast(location)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendMessageToBroadcast(location: LatLng) {
        val broadcastIntent = Intent(BROADCAST_INTENT_FILTER).apply {
            putExtra(MAPS_FRAGMENT_BROADCAST_EXTRA, location)
        }
        sendBroadcast(broadcastIntent)
    }
}

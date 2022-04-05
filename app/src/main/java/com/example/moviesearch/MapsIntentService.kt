package com.example.moviesearch

import android.app.IntentService
import android.content.Intent
import android.location.Geocoder
import com.example.moviesearch.view.map.BROADCAST_INTENT_FILTER
import com.example.moviesearch.view.map.MAPS_FRAGMENT_BROADCAST_EXTRA
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

const val MAP_SERVICE_EXTRA = "MapServiceExtra"
const val LATITUDE = "LATITUDE"
const val LONGITUDE = "LONGITUDE"
const val MAP_SERVICE_TYPE = "MapServiceTYPE"
const val INIT_SEARCH_BY_ADDRESS = "INIT_SEARCH_BY_ADDRESS"
const val GET_ADDRESS_FOR_TEXT_VIEW = "GET_ADDRESS_FOR_TEXT_VIEW"
const val MAPS_FRAGMENT_TYPE = "MAPS_FRAGMENT_BROADCAST_EXTRA"


class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val geoCoder = Geocoder(applicationContext)

        when (intent?.getStringExtra(MAP_SERVICE_TYPE)) {
            INIT_SEARCH_BY_ADDRESS -> {
                val searchText = intent.getStringExtra(MAP_SERVICE_EXTRA)

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
            GET_ADDRESS_FOR_TEXT_VIEW -> {
                val latitude = intent.getDoubleExtra(LATITUDE, 0.0)
                val longitude = intent.getDoubleExtra(LONGITUDE, 0.0)

                try {
                    val addresses =
                        geoCoder.getFromLocation(latitude,
                            longitude, 1)
                    sendMessageToBroadcast2(addresses[0].getAddressLine(0))

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun sendMessageToBroadcast(location: LatLng) {
        val broadcastIntent = Intent(BROADCAST_INTENT_FILTER).apply {
            putExtra(MAPS_FRAGMENT_BROADCAST_EXTRA, location)
            putExtra(MAPS_FRAGMENT_TYPE, INIT_SEARCH_BY_ADDRESS)
        }
        sendBroadcast(broadcastIntent)
    }

    private fun sendMessageToBroadcast2(address: String) {
        val broadcastIntent = Intent(BROADCAST_INTENT_FILTER).apply {
            putExtra(MAPS_FRAGMENT_BROADCAST_EXTRA, address)
            putExtra(MAPS_FRAGMENT_TYPE, GET_ADDRESS_FOR_TEXT_VIEW)
        }
        sendBroadcast(broadcastIntent)
    }
}

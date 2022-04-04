package com.example.moviesearch.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.MAP_SERVICE_EXTRA
import com.example.moviesearch.MyIntentService
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentMapsMainBinding
import com.example.moviesearch.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_maps_main.*

const val REQUEST_CODE_GPS = 12345
const val DEFAULT_ZOOM = 15f
const val BROADCAST_INTENT_FILTER = "BROADCAST_INTENT_FILTER"
const val MAPS_FRAGMENT_BROADCAST_EXTRA = "MAPS_FRAGMENT_BROADCAST_EXTRA"

class MapsFragment : Fragment() {

    var _binding: FragmentMapsMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapsViewModel by lazy {
        ViewModelProvider(this)[MapsViewModel::class.java]
    }

    private val markers: ArrayList<Marker> = arrayListOf()

    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val initialPlace = LatLng(52.52000659999999, 13.404953999999975)
        googleMap.addMarker(
            MarkerOptions().position(initialPlace).title(getString(R.string.marker_start))
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
        googleMap.setOnMapLongClickListener { latLng ->
            getAddressAsync(latLng.latitude, latLng.longitude)
            addMarkerToArray(latLng)
            drawLine()
        }
        activateMyLocation(googleMap)
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getParcelableExtra<LatLng>(MAPS_FRAGMENT_BROADCAST_EXTRA)?.let { location ->
                val searchText = searchAddress.text.toString()
                setMarker(location, searchText, R.drawable.marker1)
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        location,
                        DEFAULT_ZOOM
                    )
                )
            }
        }
    }

    private fun getAddressAsync(latitude: Double, longitude: Double) {
        context?.let {
            val geoCoder = Geocoder(it)
            viewModel.getAddressForTextView(geoCoder, latitude, longitude)
        }
    }

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(),
            R.drawable.android_pin)
        markers.add(marker)
    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Int,
    ): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(5f)
            )
        }
    }

    private fun activateMyLocation(googleMap: GoogleMap) {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    googleMap.isMyLocationEnabled = true
                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                }
                else -> {
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_CODE_GPS
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        checkPermissionsResult(requestCode, grantResults)
    }

    private fun checkPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_GPS -> {
                var grantedPermissions = 0
                if ((grantResults.isNotEmpty())) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size != grantedPermissions) {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
                return
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_INTENT_FILTER))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        viewModel.textAddressLiveData.observe(viewLifecycleOwner, { textAddress.text = it })
        initSearchByAddress()
    }

    private fun initSearchByAddress() {
        binding.buttonSearch.setOnClickListener {
            context?.let { context ->
                context.startService(Intent(context, MyIntentService::class.java).apply {
                    putExtra(MAP_SERVICE_EXTRA, searchAddress.text.toString())
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        context?.unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    companion object {
        fun newInstance() = MapsFragment()
    }
}



package com.lambton.bikr.activity

import android.Manifest
import android.content.pm.PackageManager
import com.lambton.bikr.R

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.easywaylocation.draw_path.DirectionUtil
import com.example.easywaylocation.draw_path.PolyLineDataBean
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, DirectionUtil.DirectionCallBack {

    override fun pathFindFinish(polyLineDetails: HashMap<String, PolyLineDataBean>) {
        for (i in polyLineDetails.keys){
            Log.v("sample", polyLineDetails[i]?.time!!)
        }
    }

    private lateinit var mMap: GoogleMap
    private var wayPoints:ArrayList<LatLng> = ArrayList()
    var currentLat: Double = 0.0
    var currentLong: Double = 0.0
    var destLat: Double = 0.0
    var destLng: Double = 0.0
    var destName: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        supportActionBar?.title = "Map"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        currentLat = intent.getDoubleExtra("current_lat",0.0)
        currentLong = intent.getDoubleExtra("current_long",0.0)

        destLat = intent.getDoubleExtra("dest_lat",0.0)
        destLng = intent.getDoubleExtra("dest_long",0.0)

        destName = intent.getStringExtra("dest_name").toString()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return
        }
        mMap.isMyLocationEnabled = true
        //Place current location marker
        val latLng = LatLng(destLat, destLng)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(destName)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        mMap!!.addMarker(markerOptions)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentLat, currentLong),16F))
        val directionUtil = DirectionUtil.Builder()
                .setDirectionKey("AIzaSyAWjJW17LKEA6MvGVsQhXTwWO-xRJqdRqk")
                .setOrigin(LatLng(currentLat, currentLong))
                .setWayPoints(wayPoints)
                .setGoogleMap(mMap)
                .setPathAnimation(false)
                .setPolyLinePrimaryColor(Color.BLUE)
                .setPolyLineWidth(14)
                .setCallback(this)
                .setDestination(LatLng(destLat, destLng))
                .build()

        directionUtil.drawPath()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
}
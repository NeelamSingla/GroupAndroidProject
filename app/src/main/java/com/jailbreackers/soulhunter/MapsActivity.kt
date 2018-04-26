package com.jailbreackers.soulhunter

import android.location.Geocoder
import android.location.Address
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import java.util.jar.Manifest
import com.google.android.gms.maps.UiSettings
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import com.google.android.gms.maps.model.*
import java.util.*


@SuppressLint("ByteOrderMark")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    lateinit var locationManager: LocationManager
    private val LOCATION_REQUEST_CODE = 101
    private var mMaker: Marker? = null


    @SuppressLint("MissingPermission")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    //Runtime Permission Check
    private fun checkPermission() {
        val getUserCurrentLocation: GetUserCurrentLocation

        getUserCurrentLocation = GetUserCurrentLocation()

        if (ActivityCompat.checkSelfPermission(this@MapsActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true)
            mMap.getUiSettings().setMyLocationButtonEnabled(true)
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3,
                    5f,
                    getUserCurrentLocation)
        } else {
            ActivityCompat.requestPermissions(this@MapsActivity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_REQUEST_CODE)
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermission()
    }

    var curLocation: Location? = null

    inner class GetUserCurrentLocation : LocationListener {
        constructor() {
            curLocation = Location("me")
            curLocation!!.longitude =18.0602
            curLocation!!.latitude = 59.3656

        }


        override fun onLocationChanged(location: Location?) {
            curLocation = location

            if (mMaker != null) {
                mMap!!.clear()

            }

            var latitude = curLocation!!.latitude
            var longitide = curLocation!!.longitude
            val latLng = LatLng(latitude, longitide)


           mMaker = mMap!!.addMarker( MarkerOptions()

                    .position(latLng)
                    .title("I Am Here !!!!")
                   .icon (BitmapDescriptorFactory.fromResource(R.drawable.vampire_icon)))

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))





            val geocoder = Geocoder(this@MapsActivity)

            // Address found using the Geocoder.
            var addresses: List<Address> = emptyList()
            addresses = geocoder.getFromLocation(latitude, longitide, 1)

            val cityName = addresses[0].getAddressLine(0)
            //val stateName = addresses[0].getAddressLine(1)
            //val countryName = addresses[0].getAddressLine(2)
            Toast.makeText(this@MapsActivity, " Current Location Is-" + cityName, Toast.LENGTH_SHORT).show()


        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

    }

    var setOfCoins = ArrayList<Coin>()
    fun addCoins() {
        // in the next step we should get the marker location and create the location of the coins close to marker location
        setOfCoins.add(

                Coin((R.drawable.coin_icon)
                        , "20 Dollar"
                        , 2.2

                        //    ,   59.327010    ,18.071384


                        , curLocation?.latitude!!
                        , curLocation?.longitude!!
                )


        )


    }

    fun displayCoin() {
        addCoins()

        for (i in 0..setOfCoins.size - 1) {

            //  if (setOfCoins[i].isCatch == false) {
            val coinLocation = LatLng(setOfCoins[i].location!!.latitude, setOfCoins[i].location!!.longitude)

            mMap.addMarker(
                    MarkerOptions()
                            .position(coinLocation)
                            .title(setOfCoins[i].value.toString() + " €")
                            .snippet(setOfCoins[i].description)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.coin_icon))
            )
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coinLocation, 14f))

            // }

        }
    }
}


/*
package com.jailbreackers.soulhunter
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.concurrent.ThreadPoolExecutor

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        checkPermision()

        displayCoin()
    }

    val accessLocation = 123


    fun checkPermision() {

       if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), accessLocation)
           }

       }

        getUserLoaction()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            accessLocation -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLoaction()
                } else {
                    Toast.makeText(this, "Location permision is denied", Toast.LENGTH_LONG).show()
                }
            }


        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun getUserLoaction() {
        Toast.makeText(this, "Location is accessed", Toast.LENGTH_LONG).show()
        val myLocation = MyLocationListener()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 5f, myLocation)
        val myThread = MyThread()
        myThread.start()
    }

    */
/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 *//*

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }
  //  val sthlm:Location= LatLng(59.327010,18.071384) as Location
    var myLocation: Location? = null

    inner class MyLocationListener : LocationListener {
        constructor() {
            myLocation = Location("me")
            myLocation!!.longitude = 0.0
            myLocation!!.latitude = 0.0

        }

        override fun onLocationChanged(location: Location?) {
            myLocation = location
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }




    var oldLocation: Location? = null

    inner class MyThread : Thread {
        constructor() : super() {
            oldLocation = Location("oldLocation")
            oldLocation!!.latitude = 59.327010
            oldLocation!!.longitude = 18.071384
        }

        override fun run() {
            while (true) {
                try {
                    if (oldLocation?.distanceTo(myLocation) == 0f) {
                        continue
                    }
                    oldLocation = myLocation

                    runOnUiThread {
                        mMap!!.clear()

                       // Add a marker in Sydney and move the camera
                        val sydney = LatLng(myLocation!!.latitude, myLocation!!.longitude)
                        mMap.addMarker(
                                MarkerOptions()
                                        .position(sydney)
                                        .title("Mohamad")
                                        .snippet("Get the souls")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.vampire_icon))

                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f))

                        */
/*for (i in 0..setOfCoins.size-1) {

                          //  if (setOfCoins[i].isCatch == false) {
                                val coinLocation = LatLng(setOfCoins[i].location!!.latitude, setOfCoins[i].location!!.longitude)

                                mMap.addMarker(
                                        MarkerOptions()
                                                .position(coinLocation)
                                                .title(setOfCoins[i].value.toString() + " €")
                                                .snippet(setOfCoins[i].description)
                                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.coin_icon))
                                             )
                                  // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coinLocation, 14f))

                           // }

                        }*//*

                    }

                    Thread.sleep(5000)
                } catch (ex: Exception) {
                }
            }
        }
    }






}



*/

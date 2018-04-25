package com.jailbreackers.soulhunter

<<<<<<< HEAD
import android.annotation.SuppressLint
=======
>>>>>>> ae86bb6c62e0851cc9eb356a062b7b3adec05d32
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
<<<<<<< HEAD
=======

>>>>>>> ae86bb6c62e0851cc9eb356a062b7b3adec05d32
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
    }
<<<<<<< HEAD
    val accessLocation=123
    fun checkPermision(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission
                            .ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest
                        .permission.ACCESS_FINE_LOCATION),accessLocation)
=======

    val accessLocation = 123


    fun checkPermision() {


        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), accessLocation)
>>>>>>> ae86bb6c62e0851cc9eb356a062b7b3adec05d32
            }
        }

        getUserLoaction()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
<<<<<<< HEAD
       when(requestCode){
           accessLocation->{
               if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   getUserLoaction()
               }
               else {
                   Toast.makeText(this,"Location permision is denied",Toast.LENGTH_LONG).show()

               }
           }
       }
=======
        when (requestCode) {
            accessLocation -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLoaction()
                } else {
                    Toast.makeText(this, "Location permision is denied", Toast.LENGTH_LONG).show()
                }
            }


        }
>>>>>>> ae86bb6c62e0851cc9eb356a062b7b3adec05d32

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

<<<<<<< HEAD
    fun getUserLoaction(){
        Toast.makeText(this,"Location is accessed",Toast.LENGTH_LONG).show()

        var myLocation= MylocationListener()
        var locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // not an error Permission alreaded requested
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f, myLocation)
        var mythread=myThread()
        mythread.start()
=======
    fun getUserLoaction() {
        Toast.makeText(this, "Location is accessed", Toast.LENGTH_LONG).show()
        val myLocation = MyLocationListener()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 5f, myLocation)
        val myThread = MyThread()
        myThread.start()
>>>>>>> ae86bb6c62e0851cc9eb356a062b7b3adec05d32
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

<<<<<<< HEAD
        // Add a marker in Sydney and move the camera

    }
    var location:Location?=null
// get user location
    inner class MylocationListener:LocationListener {
    constructor(){
        location=Location("Start")
        location!!.latitude=0.0
        location!!.longitude=0.0
    }
    override fun onLocationChanged(p0: Location?) {
        location=p0
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
    //inner class MylocationListner:LocationListener{ }
inner class myThread:Thread{
    constructor()

        override fun run() {
        while (true) {
            try {
                runOnUiThread {
                    mMap.clear()
                    val stockholm = LatLng(location!!.latitude, location!!.longitude)
                    mMap.addMarker(
                            MarkerOptions()
                                    .position(stockholm)
                                    .title("Mohamad")
                                    .snippet("Get the souls")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.vampire_icon))

                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stockholm,14f ))
                }
                Thread.sleep(1000)
            } catch (ex:Exception){}
        }
    }


}
=======
    }

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
            oldLocation!!.latitude = 59.339243
            oldLocation!!.longitude = 18.059742
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
                    }


                    Thread.sleep(5000)
                } catch (ex: Exception) {
                }
            }
        }
    }

>>>>>>> ae86bb6c62e0851cc9eb356a062b7b3adec05d32
}




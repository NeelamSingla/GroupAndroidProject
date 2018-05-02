package com.jailbreackers.soulhunter

import android.location.Geocoder
import android.location.Address
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.util.Log
import com.google.android.gms.maps.model.*
import java.util.*

//Author Chaitali
@SuppressLint("ByteOrderMark")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var  mMap: GoogleMap

    lateinit var locationManager: LocationManager
    private val LOCATION_REQUEST_CODE = 101
    private var mMaker: Marker?=null


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
                PackageManager.PERMISSION_GRANTED ) {
            mMap.setMyLocationEnabled(true)
            mMap.getUiSettings().setMyLocationButtonEnabled(true)
            mMap.getUiSettings().setZoomControlsEnabled(true)
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3,
                    5f,
                    getUserCurrentLocation )
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
       try
        {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle))
            if (!success)
            {
                Log.e("error","Style parsing failed.")
            }
        }
        catch (e: Resources.NotFoundException) {
            Log.e("error", "Can't find style. Error: ", e)
        }
        checkPermission()
    }



    inner class GetUserCurrentLocation:LocationListener {

        var latitude : Double = 0.toDouble()
        var longitide: Double = 0.toDouble()
       // Latitude: N 59° 19' 58.0372" | Longitude: E 18° 3' 52.1572"

        constructor()
        {

            latitude=59.331210
            longitide=18.061525
        }


        override fun onLocationChanged(location: Location?) {


            if(mMaker!=null)
            {
                mMap.clear()

            }

            latitude = location!!.latitude
            longitide = location!!.longitude

            displayCoin(location)



            val latLng = LatLng(latitude, longitide)

            //Resize marker on map
            val icon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.gingerbread)
            val height = 140
            val width = 120
            val bitMapDraw = resources.getDrawable(R.drawable.gingerbread) as BitmapDrawable
            val b = bitMapDraw.bitmap
            val playerMarker = Bitmap.createScaledBitmap(b, width, height, false)

            //Put marker on map
            mMaker = mMap!!.addMarker(MarkerOptions()
                    .position(latLng)
                    .title("You Are Here !!!!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.gingerbread))
                    .icon(BitmapDescriptorFactory.fromBitmap(playerMarker)))

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12f))

            //Get Location Details
            val geocoder = Geocoder(this@MapsActivity)
            // Address found using the Geocoder.
            var addresses: List<Address> = emptyList()
            addresses = geocoder.getFromLocation(latitude,longitide,1)

            val cityName = addresses[0].getAddressLine(0)

            //Show Location Details
            Toast.makeText(this@MapsActivity," Current Location Is-"+ cityName , Toast.LENGTH_SHORT).show()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

    }

    var setOfCoins = ArrayList<Coin>()



    fun createCoins(location: Location) {
        // in the next step we should get the marker location and create the location of the coins close to marker location
        var lat:Double=location.latitude
        var log:Double=location.longitude

        setOfCoins.add(

                Coin(
                        (R.drawable.coin_icon)
                        , "20 Dollar"
                        , 2.2
                        ,  lat+ generate()
                        ,  log+ generate()
                )


        )
        setOfCoins.add(

                Coin(
                        (R.drawable.coin_icon)
                        , "20 Dollar"
                        , 6.2
                        ,  lat + generate()
                        ,  log + generate()
                )


        )
        setOfCoins.add(

                Coin(
                        (R.drawable.coin_icon)
                        , "20 Dollar"
                        , 2.2
                        ,  lat + generate()
                        ,  log + generate()
                )


        )

    }
    var isTheFirstTime = true

    fun displayCoin(location:Location) {

        if (isTheFirstTime) {
            createCoins(location)
            isTheFirstTime = false

        }



        val bitMapDraw = resources.getDrawable(R.drawable.coin_icon) as BitmapDrawable
        val b = bitMapDraw.bitmap
        val playerMarker = Bitmap.createScaledBitmap(b, 180, 180, false)


        for (i in 0..setOfCoins.size - 1) {


            if (setOfCoins[i].isCatch == false) {

                if (location.distanceTo(setOfCoins[i].location) > 50) {

                    val coinLocation = LatLng(setOfCoins[i].location!!.latitude, setOfCoins[i].location!!.longitude)

                    mMap.addMarker(
                            MarkerOptions()
                                    .position(coinLocation)
                                    .title(setOfCoins[i].value.toString() + " $")
                                    .snippet(setOfCoins[i].description)
                                    .icon(BitmapDescriptorFactory.fromBitmap(playerMarker)))
                     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coinLocation, 14f))
                } else {
                    // catch it
                    setOfCoins[i].isCatch = true
                    // get the values( the points)

                    // remove from arraylist
                    setOfCoins[i].isCatch =true
                //    setOfCoins.remove(setOfCoins[i])

                }

            }

        }
    }

    fun generate():Double
    {
        val p=Math.random()

        return p/200

    }


}



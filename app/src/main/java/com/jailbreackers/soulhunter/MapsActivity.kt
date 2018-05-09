package com.jailbreackers.soulhunter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.*
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

//Author Chaitali
@SuppressLint("ByteOrderMark")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var  mMap: GoogleMap

    lateinit var locationManager: LocationManager
    private val LOCATION_REQUEST_CODE = 101
    private var mMaker: Marker?=null

    private var sound: SoundPlayer? = null

    @SuppressLint("MissingPermission")
    var scoreLabel: TextView?=null
    var distancelabel: TextView?=null
    var caloriesLabel:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        scoreLabel=findViewById<View>(R.id.scoreLabel) as TextView
        distancelabel=findViewById<View>(R.id.distancelabel) as TextView
        caloriesLabel= findViewById<View>(R.id.calories)as TextView

        scoreLabel!!.setText(" Score : 00 ")
        distancelabel!!.setText(" Distance : 00 m ")
        caloriesLabel!!.setText("Calories: 00")

        sound = SoundPlayer(this)

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
            val icon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.runner)
            val height = 140
            val width = 120
            val bitMapDraw = resources.getDrawable(R.drawable.runner) as BitmapDrawable
            val b = bitMapDraw.bitmap
            val playerMarker = Bitmap.createScaledBitmap(b, width, height, false)

            //Put marker on map
            mMaker = mMap!!.addMarker(MarkerOptions()
                    .position(latLng)
                    .title("You Are Here !!!!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.runner))
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

    var coins = ArrayList<Coin>()
    lateinit var oldLocation: Location

    fun createSingleCoin(location: Location): Coin {
        var latOfPlayer: Double = location.latitude
        var logOfPlayer: Double = location.longitude

        var latOfCoin: Double = latOfPlayer + generate()
        var logOfCoin: Double = logOfPlayer + generate()

        return Coin(
                (R.drawable.coin_icon),
                "Coin",
                calculateCoinValue(latOfPlayer, logOfPlayer, latOfCoin, logOfCoin),
                latOfCoin,
                logOfCoin
        )
    }


    fun createCoins(location: Location) {
        // in the next step we should get the marker location and create the location of the coins close to marker location
        var latOfPlayer:Double=location.latitude
        var logOfPlayer:Double=location.longitude

        var latOfCoin1: Double = latOfPlayer + generate()
        var logOfCoin1: Double = logOfPlayer + generate()

        var latOfCoin2: Double = latOfPlayer + generate()
        var logOfCoin2: Double = logOfPlayer + generate()

        var latOfCoin3: Double = latOfPlayer + generate()
        var logOfCoin3: Double = logOfPlayer + generate()


        coins.add(

                Coin(
                        (R.drawable.coin_icon)
                        , "Coin1"
                        ,  calculateCoinValue(latOfPlayer, logOfPlayer, latOfCoin1, logOfCoin1)
                        ,  latOfCoin1
                        ,  logOfCoin1
                )


        )
        coins.add(

                Coin(
                        (R.drawable.coin_icon)
                        , "Coin2"
                        ,  calculateCoinValue(latOfPlayer, logOfPlayer, latOfCoin2, logOfCoin2)
                        ,  latOfCoin2
                        ,  logOfCoin2
                )


        )
        coins.add(

                Coin(
                        (R.drawable.coin_icon)
                        , "Coin3"
                        ,  calculateCoinValue(latOfPlayer, logOfPlayer, latOfCoin3, logOfCoin3)
                        ,  latOfCoin3
                        ,  logOfCoin3
                )


        )

    }

    fun calculateCoinValue(latOfPlayer: Double, logOfPlayer: Double, latOfCoin: Double, logOfCoin: Double ): Double {
        var distanceToCoinArray = floatArrayOf(0.0f)
        Location.distanceBetween(latOfPlayer, logOfPlayer, latOfCoin, logOfCoin, distanceToCoinArray)

        val distanceToCoin: Float = distanceToCoinArray[0]
        var coinValue: Double = 0.0

        // Coin value if distance greater than 600 m
        if (distanceToCoin > 300) {
            coinValue = 10.0
        }else{
            coinValue = 5.0
        }


        return coinValue
    }



    var distance: Float = 0f
    var isTheFirstTime = true
    var score: Double = 0.0

    fun displayCoin(location: Location) {

        if (isTheFirstTime) {
            createCoins(location)
            oldLocation=location
            isTheFirstTime = false
        }
        distance = distance+location.distanceTo(oldLocation)
        distancelabel!!.setText(" Distance: ${distance.toInt() } m ")
        caloriesLabel!!.setText("Calories: ${   (distance * 15/320).toInt()   } ")
        //update the textview



        oldLocation=location

        val bitMapDraw = resources.getDrawable(R.drawable.coin_ten) as BitmapDrawable
        val b = bitMapDraw.bitmap
        val playerMarker = Bitmap.createScaledBitmap(b, 160, 160, false)



        val bitMapCoinFive = resources.getDrawable(R.drawable.coin_five) as BitmapDrawable
        val bb = bitMapCoinFive.bitmap
        val coinFive = Bitmap.createScaledBitmap(bb, 160, 160, false)






        for (i in 0..coins.size - 1) {


            if (coins[i].isCatch == false) {

                if (location.distanceTo(coins[i].location) > 50) {

                    val coinLocation = LatLng(coins[i].location!!.latitude, coins[i].location!!.longitude)

                    if(coins[i].value == 5.0) {

                        mMap.addMarker(
                                MarkerOptions()
                                        .position(coinLocation)
                                        .title(coins[i].value.toString())
                                        .snippet("lat=" + coins[i].location!!.latitude + ", long=" + coins[i].location!!.longitude)

                                        //.snippet(coins[i].description)
                                        .icon(BitmapDescriptorFactory.fromBitmap(coinFive)))
                        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coinLocation, 14f))
                    }else{
                        mMap.addMarker(
                                MarkerOptions()
                                        .position(coinLocation)
                                        .title(coins[i].value.toString())
                                        .snippet("lat=" + coins[i].location!!.latitude + ", long="+ coins[i].location!!.longitude)

                                        //.snippet(coins[i].description)
                                        .icon(BitmapDescriptorFactory.fromBitmap(playerMarker)))

                    }
                } else {
                    // catch it
                    coins[i].isCatch = true
                    sound!!.playHitSound()
                    // get the values( the points)
                    score = score + coins[i].value!!
                    scoreLabel!!.setText(" Score : ${score} ")
                    //play Sound





                    // remove from arraylist
                    coins.set(i, createSingleCoin(location))



                    //display single coin on the screen
                    if(coins[i].value == 5.0) {
                        val coinLocation = LatLng(coins[i].location!!.latitude, coins[i].location!!.longitude)
                        mMap.addMarker(
                                MarkerOptions()
                                        .position(coinLocation)
                                        .title(coins[i].value.toString())
                                        .snippet("lat=" + coins[i].location!!.latitude + ", long=" + coins[i].location!!.longitude)

                                        //.snippet(coins[i].description)
                                        .icon(BitmapDescriptorFactory.fromBitmap(coinFive)))
                    }else{

                          val coinLocation = LatLng(coins[i].location!!.latitude, coins[i].location!!.longitude)
                           mMap.addMarker(
                                   MarkerOptions()
                                   .position(coinLocation)
                                   .title(coins[i].value.toString())
                                   .snippet("lat=" + coins[i].location!!.latitude + ", long=" + coins[i].location!!.longitude)
                                   .icon(BitmapDescriptorFactory.fromBitmap(playerMarker)))






                    }



                }

            }

        }
        //send the intent to display the score
        var preferences = getSharedPreferences("PREFS", 0)
        var editor = preferences.edit()
        editor.putInt("lastScore", score.toInt() )
        editor.commit()
    }

    fun generate():Double
    {
        var p = Math.random()

        var randomSign = Math.random()

        if(randomSign < 0.5) {
            p *= -1
        }


        return p/200

    }





override
fun onBackPressed(){
    var intent = Intent(applicationContext, HomeMenuActivity::class.java)
    startActivity(intent)
    finish()

}
}

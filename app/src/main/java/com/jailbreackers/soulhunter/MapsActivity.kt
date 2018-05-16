package com.jailbreackers.soulhunter

import android.location.Geocoder
import android.location.Address
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("ByteOrderMark")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    lateinit var locationManager: LocationManager
    private val LOCATION_REQUEST_CODE = 101
    private var mMaker: Marker? = null

    private var sound: SoundPlayer? = null

    @SuppressLint("MissingPermission")
    var scoreLabel: TextView? = null
    var distancelabel: TextView? = null
    var caloriesLabel: TextView? = null
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        scoreLabel = findViewById<View>(R.id.scoreLabel) as TextView
        distancelabel = findViewById<View>(R.id.distancelabel) as TextView
        caloriesLabel = findViewById<View>(R.id.calories) as TextView

        scoreLabel!!.text = " Score : 00 "
        distancelabel!!.text = " Distance : 00 m "
        caloriesLabel!!.text = "Calories: 00"

        sound = SoundPlayer(this)

    }

    //Runtime Permission Check
    private fun checkPermission() {
        val getUserCurrentLocation: GetUserCurrentLocation

        getUserCurrentLocation = GetUserCurrentLocation()

        if (ActivityCompat.checkSelfPermission(this@MapsActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
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
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle))
            if (!success) {
                Log.e("error", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("error", "Can't find style. Error: ", e)
        }
        checkPermission()
    }


    inner class GetUserCurrentLocation : LocationListener {

        var latitude: Double = 0.toDouble()
        var longitide: Double = 0.toDouble()
        // Latitude: N 59° 19' 58.0372" | Longitude: E 18° 3' 52.1572"

        constructor() {

            latitude = 59.331210
            longitide = 18.061525
        }


        override fun onLocationChanged(location: Location?) {


            if (mMaker != null) {
                mMap.clear()

            }

            latitude = location!!.latitude
            longitide = location.longitude

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
            mMaker = mMap.addMarker(MarkerOptions()
                    .position(latLng)
                    .title("You Are Here !!!!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.runner))
                    .icon(BitmapDescriptorFactory.fromBitmap(playerMarker)))
            val cameraPosition = CameraPosition.builder()
                    .target(latLng)
                    .zoom(15f)
                    .bearing(90f)
                    .build()

            // Animate the change in camera view over 2 seconds
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                    2000, null)

            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12f))

            //Get Location Details
            val geocoder = Geocoder(this@MapsActivity)
            // Address found using the Geocoder.
            var addresses: List<Address> = emptyList()
            addresses = geocoder.getFromLocation(latitude, longitide, 1)

            val cityName = addresses[0].getAddressLine(0)

            //Show Location Details
            //     Toast.makeText(this@MapsActivity," Current Location Is-"+ cityName , Toast.LENGTH_SHORT).show()
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


    fun createCoins(location: Location) {
        // in the next step we should get the marker location and create the location of the coins close to marker location
        var lat: Double = location.latitude
        var log: Double = location.longitude

        coins.add(

                Coin(
                        (R.drawable.treasure_box)
                        , "20 points"
                        , 10
                        , lat + generate()
                        , log + generate()
                )


        )
        coins.add(

                Coin(
                        (R.drawable.treasure_box)
                        , "20 points"
                        , 60
                        , lat + generate()
                        , log + generate()
                )


        )
        coins.add(

                Coin(
                        (R.drawable.treasure_box)
                        , "20 points"
                        , 125
                        , lat + generate()
                        , log + generate()
                )


        )

    }

    var distance: Float = 0f
    var calories: Float = 0f
    var isTheFirstTime = true
    var score: Int = 0

    fun displayCoin(location: Location) {

        if (isTheFirstTime) {
            createCoins(location)
            oldLocation = location
            isTheFirstTime = false
        }
        distance += location.distanceTo(oldLocation)
        calories = (distance * 15 / 320)
        distancelabel!!.text = " Distance:${distance.toInt()} "
        caloriesLabel!!.text = "Calories: ${calories.toInt()} "
        //update the textview


        oldLocation = location

        val bitMapDraw = resources.getDrawable(R.drawable.treasure_box) as BitmapDrawable
        val b = bitMapDraw.bitmap
        val playerMarker = Bitmap.createScaledBitmap(b, 180, 180, false)


        for (i in 0..coins.size - 1) {


            //    if (coins[i].isCatch == false) {

            if (location.distanceTo(coins[i].location) > 50) {

                val coinLocation = LatLng(coins[i].location!!.latitude, coins[i].location!!.longitude)

                mMap.addMarker(
                        MarkerOptions()
                                .position(coinLocation)
                                .title(coins[i].value.toString() + " points")
                         
                                .icon(BitmapDescriptorFactory.fromBitmap(playerMarker)))
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coinLocation, 14f))
            } else {
                // catch it

                // play the sound
                sound!!.playHitSound()

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Congratulations!! You Win ${coins[i].value} \$")

                dialogBuilder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int -> }
                dialogBuilder.create().show()
                //Toast.makeText(this@MapsActivity," congratulations!!! you got ${coins[i].value} points " , Toast.LENGTH_LONG).show()
                // get the values( the points)
                score += coins[i].value
                scoreLabel!!.text = " Score : ${score} "
                coins[i].changeLocation(location, generate())
                coins[i].generateValue()


                //   }

            }

        }
        //send the intent to display the score
        var preferences = getSharedPreferences("PREFS", 0)
        var editor = preferences.edit()
        editor.putInt("currentScore", score.toInt())
        editor.putInt("currentDistance", distance.toInt())
        editor.putInt("currentCalories", (distance * 15 / 320).toInt())
        editor.putString("date", currentDate.toString())
        editor.apply()
    }


    fun generate(): Double {
        var p = Math.random()

        var randomSign = Math.random()

        if (randomSign < 0.5) {
            p *= -1
        }


        return p / 200

    }


    override
    fun onBackPressed() {
        var intent = Intent(applicationContext, HomeMenuActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun finish(view: View) {
        startActivity(Intent(applicationContext, ScoreActivity::class.java))
    }
}

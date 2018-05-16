package com.jailbreackers.soulhunter

import android.location.Location

class Coin {


    var image: Int? = null
    var description: String? = null
    var value: Int = 0
    var location: Location? = null

    var isCatch = false

    constructor(image: Int,
                description: String,
                value: Int = 50,
                lat: Double,
                long: Double) {

        this.image = image
        this.description = description
        this.value = value
        this.location = Location("coin")
        this.location!!.latitude = lat
        this.location!!.longitude = long
    }

    fun changeLocation(location: Location, p: Double) {
        this.location!!.latitude = location!!.latitude + p
        this.location!!.longitude = location!!.longitude + p

    }

    var p = Math.random()

    fun generateValue() {

        this.value = (100 * p).toInt()

    }

}
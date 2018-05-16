package com.jailbreackers.soulhunter

import android.location.Location

/**
 * This class represents the treasure-box
 *
 */

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

    /**
     * this function changes the location of the box by getting the new location of the play and create a random nearby location
     */
    fun changeLocation(location: Location, p: Double) {
        this.location!!.latitude = location!!.latitude + p
        this.location!!.longitude = location!!.longitude + p
    }

    var p = Math.random()

    /**
     * generate random value for the box
     */
    fun generateValue() {
        this.value = (100 * p).toInt()
    }

}
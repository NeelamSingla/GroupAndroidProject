package com.jailbreackers.soulhunter


import java.util.*

data class Currentlocation(val latitudeIn:Double, val longitudeIn: Double) {

    var currentLongitude : Double = latitudeIn
    var currentLatitude  : Double = longitudeIn
}

data class RandomLocation(val randomLat : Double,val randomLon : Double){

    var lat : Double = randomLat
    var lon : Double = randomLon
}

class RandomPointsRepository{

    private var coinPointsRepository : List<RandomLocation> = mutableListOf()

    fun addRandomPointsToList(currentlocation: Currentlocation)  : List<RandomLocation>   {

        coinPointsRepository += addRandomPoints(currentlocation)
        return coinPointsRepository

    }


    fun addRandomPoints( currentlocation: Currentlocation): RandomLocation {

        //core of the function
        var latitude: Double = currentlocation.currentLatitude
        var longitude: Double = currentlocation.currentLongitude

        val latitudeRange2km: Double = 0.0076
        val longitudeRange2km: Double = 0.0182


        val random = Random()
        val randomLatitude: Double = random.nextDouble() * latitudeRange2km
        val randomLongitude: Double = random.nextDouble() * longitudeRange2km

        if(random.nextDouble() < 0.5){
            latitude += randomLatitude

        } else{
            latitude -= randomLatitude
        }

        if(random.nextDouble() < 0.5){
            longitude += randomLongitude

        }else{
            longitude -= randomLongitude

        }

        val randomLocation = RandomLocation(latitude,longitude)
        return randomLocation
    }
}

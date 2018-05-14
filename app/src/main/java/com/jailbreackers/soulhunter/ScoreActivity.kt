package com.jailbreackers.soulhunter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {

    internal var current_score: TextView? = null
    internal var best1_score: TextView? = null
    internal var best2_score: TextView? = null
    internal var best3_score: TextView? = null
    internal var currentScore: Int = 0
    internal var currentDistance: Int = 0
    internal var currentCalories: Int = 0
    internal var best1: Int = 0
    internal var best2: Int = 0
    internal var best3: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        current_score = findViewById<View>(R.id.current_score) as TextView?
        best1_score = findViewById<View>(R.id.best1) as TextView?
        best2_score = findViewById<View>(R.id.best2) as TextView?
        best3_score = findViewById<View>(R.id.best3) as TextView?
        val preferences = getSharedPreferences("PREFS", 0)
        currentScore = preferences.getInt("currentScore", 0)
        currentDistance= preferences.getInt("currentDistance", 0)
        currentCalories= preferences.getInt("currentCalories", 0)
        best1 = preferences.getInt("best1", 0)
        best2 = preferences.getInt("best2", 0)
        best3 = preferences.getInt("best3", 0)
        if (currentScore > best3) {

            best3 = currentScore
            val editor = preferences.edit()
            editor.putInt("best3", best3)
            editor.putInt("currentCalories", currentCalories)
            editor.putInt("currentDistance", currentDistance)
            editor.apply()
        }
        if (currentScore > best2) {
            val temp = best2
            best2 = currentScore
            best3 = temp
            val editor = preferences.edit()
            editor.putInt("best3", best3)
            editor.putInt("best2", best2)
            editor.putInt("currentCalories", currentCalories)
            editor.putInt("currentDistance", currentDistance)
            editor.apply()
        }
        if (currentScore > best1) {
            val temp = best1
            best1 = currentScore
            best2 = temp
            val editor = preferences.edit()
            editor.putInt("best2", best2)
            editor.putInt("best1", best1)
            editor.putInt("currentCalories", currentCalories)
            editor.putInt("currentDistance", currentDistance)
            editor.apply()
        }
        current_score !!.text = "Current Score: " + currentScore + ", " + "Calories:" + currentCalories + ", " +  "Distance:"+ currentDistance

        best1_score !!.text = "Best1: " + best1 + ", " + "Calories:" + currentCalories+ ", " + "Distance:"+ currentDistance
        best2_score !!.text = "Best2: " + best2 + ", " + "Calories:" + currentCalories+ ", " + "Distance:"+ currentDistance
        best3_score !!.text = "Best3: " + best3 + ", " + "Calories:" + currentCalories+ ", " + "Distance:"+ currentDistance
    }
    override
    fun onBackPressed(){
        var intent = Intent(applicationContext, HomeMenuActivity::class.java)
        startActivity(intent)
        finish()

}
    fun play(view:View) {
        startActivity(Intent(getApplicationContext(), MapsActivity::class.java))
    }
}




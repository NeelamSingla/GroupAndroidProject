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
    internal var date: String = ""
    internal var currentScore: Int = 0
    internal var currentDistance: Int = 0
    internal var currentCalories: Int = 0
    internal var best1: Int = 0
    internal var best2: Int = 0
    internal var best3: Int = 0
    internal var cal1: Int = 0
    internal var cal2: Int = 0
    internal var cal3: Int = 0
    internal var dis1: Int = 0
    internal var dis2: Int = 0
    internal var dis3: Int = 0
    internal var date1: String = ""
    internal var date2: String = ""
    internal var date3: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        current_score = findViewById<View>(R.id.current_score) as TextView?
        best1_score = findViewById<View>(R.id.best1) as TextView?
        best2_score = findViewById<View>(R.id.best2) as TextView?
        best3_score = findViewById<View>(R.id.best3) as TextView?
        val preferences = getSharedPreferences("PREFS", 0)
        currentScore = preferences.getInt("currentScore", 0)
        currentDistance = preferences.getInt("currentDistance", 0)
        currentCalories = preferences.getInt("currentCalories", 0)
        date = preferences.getString("date", null)
        best1 = preferences.getInt("best1", 0)
        best2 = preferences.getInt("best2", 0)
        best3 = preferences.getInt("best3", 0)
        cal1 = preferences.getInt("cal1", 0)
        cal2 = preferences.getInt("cal2", 0)
        cal3 = preferences.getInt("cal3", 0)
        dis1 = preferences.getInt("dis1", 0)
        dis2 = preferences.getInt("dis2", 0)
        dis3 = preferences.getInt("dis3", 0)
        date1=preferences.getString("date1","")
        date2=preferences.getString("date2","")
        date3=preferences.getString("date3","")

        if (currentScore > best1) {


            best3 = best2
            best2 = best1
            best1 = currentScore

            dis3 = dis2
            dis2 = dis1
            dis1 = currentDistance

            cal3 = cal2
            cal2 = cal1
            cal1 = currentCalories

            date3 = date2
            date2 = date1
            date1 = date

        } else {

            if (currentScore > best2) {
                best3 = best2
                best2 = currentScore

                dis3 = dis2
                dis2 = currentDistance

                cal3 = cal2
                cal2 = currentCalories

                date3 = date2
                date2 = date


            } else {
                if (currentScore > best3) {
                    best3 = currentScore
                    dis3 = currentDistance
                    cal3 == currentCalories
                    date3 = date
                }

            }


        }


        val editor = preferences.edit()
        editor.putInt("best3", best3)
        editor.putInt("best2", best2)
        editor.putInt("best1", best1)
        editor.putInt("cal1", cal1)
        editor.putInt("cal2", cal2)
        editor.putInt("cal3", cal3)
        editor.putInt("dis1", dis1)
        editor.putInt("dis2", dis2)
        editor.putInt("dis3", dis3)
        editor.putInt("currentCalories", currentCalories)
        editor.putInt("currentDistance", currentDistance)
        editor.putInt("currentScore", currentScore)
        editor.putString("date1",date1)
        editor.putString("date2",date2)
        editor.putString("date3",date3)

        editor.apply()


        current_score!!.text = "Current Score: $currentScore\n Calories:$currentCalories\n Distance:$currentDistance"

        best1_score!!.text = "Date $date1\n Best1: $best1\n Calories:$cal1\n Distance:$dis1"
        best2_score!!.text = "Date $date2\n Best2: $best2\n Calories:$cal2\n Distance:$dis2"
        best3_score!!.text = "Date $date3\n Best3: $best3\n Calories:$cal3\n Distance:$dis3"
    }

    override
    fun onBackPressed() {
        var intent = Intent(applicationContext, HomeMenuActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun play(view: View) {
        startActivity(Intent(getApplicationContext(), MapsActivity::class.java))
    }
}




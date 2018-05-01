package com.jailbreackers.soulhunter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {

    internal var game_score: TextView? = null
    internal var lastScore: Int = 0
    internal var best1: Int = 0
    internal var best2: Int = 0
    internal var best3: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        game_score = findViewById<View>(R.id.my_score) as TextView?
        val preferences = getSharedPreferences("PREFS", 0)
        lastScore = preferences.getInt("lastScore", 0)
        best1 = preferences.getInt("best1", 0)
        best2 = preferences.getInt("best2", 0)
        best3 = preferences.getInt("best3", 0)
        if (lastScore > best3) {
            best3 = lastScore
            val editor = preferences.edit()
            editor.putInt("best3", best3)
            editor.apply()
        }
        if (lastScore > best2) {
            val temp = best2
            best2 = lastScore
            best3 = temp
            val editor = preferences.edit()
            editor.putInt("best3", best3)
            editor.putInt("best2", best2)
            editor.apply()
        }
        if (lastScore > best1) {
            val temp = best1
            best1 = lastScore
            best2 = temp
            val editor = preferences.edit()
            editor.putInt("best2", best2)
            editor.putInt("best1", best1)
            editor.apply()
        }
        game_score!!.text = "LAST SCORE:" + lastScore + "\n" +
                "BEST1:" + best1 + "\n" +
                "BEST2:" + best2 + "\n" +
                "BEST3:" + best3 + "\n"

    }

}

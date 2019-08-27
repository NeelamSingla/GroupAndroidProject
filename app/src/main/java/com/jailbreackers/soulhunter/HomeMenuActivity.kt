package com.jailbreackers.soulhunter

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.jailbreackers.soulhunter.R.id.*
import kotlinx.android.synthetic.main.activity_home_menu.*

/**
 * @ class HomeMenuActivity, is about User interface
 *
 */
class HomeMenuActivity : AppCompatActivity() {
    private val ADD_MAP = 1
    private val ADD_HELP = 2
    private val ADD_SCORE = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_menu)
        fab_playbutton.setOnClickListener()
        {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, ADD_MAP)
        }
        fab_score.setOnClickListener() {

            val intent = Intent(this, ScoreActivity::class.java)
            startActivityForResult(intent, ADD_SCORE)
        }
        fab_helpbutton.setOnClickListener() {

            val intent = Intent(this, HelpInstructionsActivity::class.java)
            startActivityForResult(intent, ADD_HELP)
        }
    }


    /*override
    fun onBackPressed() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Are You Sure You Want To Exit?")
        //dialogBuilder.setMessage("Kotlin is awesome")
        dialogBuilder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            System.exit(0)
        }
        dialogBuilder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(this, "Press Play ", Toast.LENGTH_SHORT).show()

        }

        dialogBuilder.create().show()

    }*/

}

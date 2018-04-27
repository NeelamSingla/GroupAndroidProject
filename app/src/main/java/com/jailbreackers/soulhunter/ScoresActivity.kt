package com.jailbreackers.soulhunter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity:AppCompatActivity() {

    val scores = listOf(
        Score(100),
        Score(200),
        Score(300)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        val adapter = MyAdapter()
        listOfScoresRecyclerView.adapter = adapter
        listOfScoresRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.listOfScores = scores
        adapter.notifyDataSetChanged()

    }



}

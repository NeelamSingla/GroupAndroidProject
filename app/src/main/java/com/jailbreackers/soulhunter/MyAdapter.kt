package com.jailbreackers.soulhunter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    //Holds references to XML file
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val scoreTextView = itemView.findViewById<TextView>(R.id.scoreTextView)

        //Binds data to the element of a single view
        fun bind(score: Score){
            scoreTextView.text = score.playerScore.toString()
        }
    }


    var listOfScores: List<Score> = emptyList()


    // Create MyViewHolder.  Inside it inflate single item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_score_item, parent, false)
        return MyViewHolder(view)

    }

    //Number of elements in the list
    override fun getItemCount(): Int {
        return listOfScores.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listOfScores[position])
    }


}
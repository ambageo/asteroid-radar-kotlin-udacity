package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import kotlinx.android.synthetic.main.list_item_asteroid.view.*

class AsteroidsAdapter(application: Application): RecyclerView.Adapter<AsteroidViewHolder>() {

    var asteroidsList = listOf<Asteroid>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_asteroid, parent,false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroidsList[position]
        holder.codename.text = asteroid.codename
        holder.approachDate.text = asteroid.closeApproachDate
        holder.isHazardous.text = asteroid.isPotentiallyHazardous.toString()

    }

    override fun getItemCount(): Int {
        Log.d("ggg", "Number of asteroids fetched: ${asteroidsList.size}" )
        return asteroidsList.size
    }
}

class AsteroidViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
val codename: TextView = itemView.findViewById(R.id.codename)
    val approachDate: TextView = itemView.findViewById(R.id.close_approach_date)
    val isHazardous: TextView = itemView.findViewById(R.id.hazardous_icon)
}

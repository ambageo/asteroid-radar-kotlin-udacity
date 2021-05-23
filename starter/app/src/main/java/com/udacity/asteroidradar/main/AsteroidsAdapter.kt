package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding
import kotlinx.android.synthetic.main.list_item_asteroid.view.*

class AsteroidsAdapter(application: Application): RecyclerView.Adapter<AsteroidViewHolder>() {

    var asteroidsList = listOf<Asteroid>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
        //val view = layoutInflater.inflate(R.layout.list_item_asteroid, parent,false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroidsList[position]
        holder.codename.text = asteroid.codename
        holder.approachDate.text = asteroid.closeApproachDate
        holder.isHazardous.setImageResource(
            if(asteroid.isPotentiallyHazardous) {
                R.drawable.ic_status_potentially_hazardous
            } else {
                R.drawable.ic_status_normal
            })

    }

    override fun getItemCount(): Int {
        Log.d("ggg", "Number of asteroids fetched: ${asteroidsList.size}" )
        return asteroidsList.size
    }
}

class AsteroidViewHolder(binding: ListItemAsteroidBinding) :RecyclerView.ViewHolder(binding.root) {
    val codename: TextView = binding.codename
    val approachDate: TextView = binding.closeApproachDate
    val isHazardous: ImageView = binding.hazardousIcon
}

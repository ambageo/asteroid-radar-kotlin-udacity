package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidsAdapter(val clickListener: AsteroidListener) :
    ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(AsteroidDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
        //val asteroid = getItem(position)
        /*
        holder.codename.text = asteroid.codename
        holder.approachDate.text = asteroid.closeApproachDate
        holder.isHazardous.setImageResource(
            if (asteroid.isPotentiallyHazardous) {
                R.drawable.ic_status_potentially_hazardous
            } else {
                R.drawable.ic_status_normal
            }
        )*/

    }

    class AsteroidViewHolder(private val binding: ListItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: AsteroidListener, item: Asteroid) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
            /*binding.codename.text = asteroid.codename
            binding.closeApproachDate.text = asteroid.closeApproachDate
            binding.hazardousIcon.setImageResource(
                if (asteroid.isPotentiallyHazardous) {
                    R.drawable.ic_status_potentially_hazardous
                } else {
                    R.drawable.ic_status_normal
                }
            )*/
        }

    }
}

class AsteroidDiffCallBack : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidListener(val clickListener:(asteroid:Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}

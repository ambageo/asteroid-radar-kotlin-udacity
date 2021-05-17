package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asteroid constructor(
    @PrimaryKey val id: Long,
                val name: String,
                val absoluteMagnitude: Double,
                val estimatedDiameter: Double,
                val isPotentiallyHazardous: Boolean,
                val relativeVelocity : Double,
                val distanceFromEarth: Double
)

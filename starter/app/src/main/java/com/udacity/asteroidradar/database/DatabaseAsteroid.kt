package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid

@Entity(tableName = "asteroid_table")
data class DatabaseAsteroid constructor(
    @PrimaryKey val id: Long,
                val name: String,
                val absoluteMagnitude: Double,
                val estimatedDiameter: Double,
                val isPotentiallyHazardous: Boolean,
                val relativeVelocity : Double,
                val distanceFromEarth: Double
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map{
        Asteroid(id = it.id,
        name = it.name,
        absoluteMagnitude = it.absoluteMagnitude,
        estimatedDiameter = it.estimatedDiameter,
        relativeVelocity = it.relativeVelocity,
        distanceFromEarth = it.distanceFromEarth)
    }
}



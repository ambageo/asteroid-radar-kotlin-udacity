package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg databaseAsteroids: DatabaseAsteroid)

    @Query("select * from asteroid_table order by approach_date")
    // No need to add suspend when returning LiveData
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("select * from asteroid_table where approach_date = :todayDate")
    fun getTodayAsteroids(todayDate: String): LiveData<List<DatabaseAsteroid>>
}

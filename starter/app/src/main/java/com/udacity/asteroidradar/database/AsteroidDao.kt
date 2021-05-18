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

    @Query("select * from asteroid_table")
    // No need to add suspend when returning LiveData
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>
}

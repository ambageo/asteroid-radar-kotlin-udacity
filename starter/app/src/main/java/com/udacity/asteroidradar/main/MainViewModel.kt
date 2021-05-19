package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidsDatabase.getInstance(application)

    private val asteroidsRepository = AsteroidsRepository(database)

init {
    getNasaAsteroids()
}

    private fun getNasaAsteroids() {
        viewModelScope.launch {
            try {
                val response = NasaApi.retrofitService.getAsteroids()
                val result = parseAsteroidsJsonResult(JSONObject(response))
                asteroidsRepository.refreshAsteroids()
                Log.d("GGG", "asteroids: ${result.size}")
            } catch (e: Exception){
                Log.d("ggg", "error: $e")
            }
        }
    }
}
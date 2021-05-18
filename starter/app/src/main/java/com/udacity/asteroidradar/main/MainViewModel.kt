package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

init {
    getNasaAsteroids()
}

    private fun getNasaAsteroids() {
        viewModelScope.launch {
            try {
                val response = NasaApi.retrofitService.getAsteroids()
                val result = parseAsteroidsJsonResult(JSONObject(response))
                Log.d("GGG", "asteroids: ${result.size}")
            } catch (e: Exception){
                Log.d("ggg", "error: $e")
            }
        }
    }
}
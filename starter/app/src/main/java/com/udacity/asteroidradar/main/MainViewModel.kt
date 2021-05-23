package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.network.NasaApiService
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidsDatabase.getInstance(application)

    private val asteroidsRepository = AsteroidsRepository(database)
    var asteroidsList = asteroidsRepository.asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
    get() = _pictureOfDay

init {
    getNasaAsteroids()
    getPictureOfDay()
}

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = NasaApi.retrofitService.getTodayImage()
                Log.d("ggg", "${pictureOfDay.value?.mediaType} ${pictureOfDay.value?.title} ${pictureOfDay.value?.url}")
            }catch (e: Exception){
                Log.d("ggg", "error: $e")
            }
        }
    }

    private fun getNasaAsteroids() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroids()
            } catch (e: Exception){
                Log.d("ggg", "error: $e")
            }
        }
    }

}
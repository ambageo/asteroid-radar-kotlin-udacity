package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidsDatabase.getInstance(application)

    private val asteroidsRepository = AsteroidsRepository(database)
    var asteroidsList = asteroidsRepository.asteroids

    /*private val _asteroidsList = MutableLiveData<List<DatabaseAsteroid>>()
    val asteroidsList: LiveData<List<DatabaseAsteroid>>
        get() = _asteroidsList*/

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetails: LiveData<Asteroid?>
        get() = _navigateToAsteroidDetails

    init {
        getNasaAsteroids()
        getPictureOfDay()
    }

    fun onAsteroidClicked(item: Asteroid) {
        _navigateToAsteroidDetails.value = item
    }

    fun onNavigationCompleted() {
        _navigateToAsteroidDetails.value = null
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = NasaApi.retrofitService.getTodayImage()
            } catch (e: Exception) {
                Log.d("ggg", "error: $e")
            }
        }
    }

    private fun getNasaAsteroids() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroids()
            } catch (e: Exception) {
                Log.d("ggg", "error: $e")
            }
        }
    }

    fun onWeekAsteroidsSelected() {
        asteroidsList = asteroidsRepository.asteroids
    }

    fun onTodayAsteroidSelected() {
        val currentDate = getCurrentDate()

        asteroidsList.value?.filter { asteroid -> asteroid.closeApproachDate == currentDate }
        asteroidsList = Transformations.map(asteroidsList){ list ->
            list.filter {
                it.closeApproachDate == currentDate
            }
        }
    }

    fun onSavedAsteroidsSelected() {
        asteroidsList = asteroidsRepository.asteroids
    }

    private fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentDate).toString()
    }
}



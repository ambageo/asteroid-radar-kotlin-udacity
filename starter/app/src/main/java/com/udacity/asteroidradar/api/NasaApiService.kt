package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "Acb3wXMWBcliyVZeNUFuY5j4YWXZ2ERNm6an7nYM"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface NasaApiService {
    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query(Constants.START_DATE) startDate:String = getCurrentDate(),
        @Query(Constants.END_DATE) endDate:String = getSevenDaysLaterDate(),
        @Query(Constants.API_KEY) apiKey:String = API_KEY
    ): Response<String>

    @GET("/planetary/apod")
    suspend fun getTodayImage(
        @Query(Constants.API_KEY) apiKey:String = API_KEY
    ): Response<PictureOfDay>
}

object NasaApi {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}
package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "PUT YOUR OWN KEY HERE"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface NasaApiService {
    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query(Constants.START_DATE) startDate:String = getCurrentDate(),
        @Query(Constants.END_DATE) endDate:String = getSevenDaysLaterDate(),
        @Query(Constants.API_KEY) apiKey:String = API_KEY
    ): String

    @GET("/planetary/apod")
    suspend fun getTodayImage(
        @Query(Constants.API_KEY) apiKey:String = API_KEY
    ): PictureOfDay
}

object NasaApi {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}
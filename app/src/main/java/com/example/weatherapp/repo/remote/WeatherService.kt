package com.example.weatherapp.repo.remote

import com.example.weatherapp.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/forecast?appid=65d00499677e59496ca2f318eb68c049")
    suspend fun getWeatherData(
        @Query("q") q : String,
        @Query("units") units: String = "imperial"
    ) : Response<WeatherData>
}
package com.example.weatherapp.repo

import android.util.Log
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.repo.remote.WeatherService
import retrofit2.Response
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherData(city: String): Response<WeatherData> {
        val weatherData = weatherService.getWeatherData(city)
        if (!weatherData.isSuccessful) {
            Log.d(TAG, "GET WEATHER DATA IS NOT SUCCESSFUL")
        }
        return weatherData
    }

    companion object {
        private const val TAG = "WEATHER_REPO"
    }
}
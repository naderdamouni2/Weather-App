package com.example.weatherapp.repo

import android.util.Log
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.repo.remote.WeatherService
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val weatherService: WeatherService) {

    suspend fun getWeatherData(q: String): Response<WeatherData> {
        return weatherService.getWeatherData(q)
//        val weatherResponse = weatherService.getWeatherData(q)
//        Log.d(TAG, "WEATHER DATA IS LOADING...")
//        emit(ApiState.Loading)
//
//        val state = if (weatherResponse.isSuccessful) {
//            Log.d(TAG, "CITY SEARCHED SUCCESSFULLY")
//            ApiState.Success(weatherResponse.body()!!)
//        } else {
//            Log.e(TAG, "ERROR FETCHING WEATHER DATA")
//            ApiState.Failure("ERROR FETCHING WEATHER DATA")
//        }
//        emit(state)
    }

    companion object {
        private const val TAG = "WEATHER_REPO"
    }
}
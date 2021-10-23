package com.example.weatherapp.viewModel

import androidx.lifecycle.*
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.repo.WeatherRepo
import com.example.weatherapp.utils.ApiState
import com.example.weatherapp.utils.QueryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    private val _weatherState: MutableLiveData<ApiState<WeatherData>> = MutableLiveData()
    val weatherState: LiveData<ApiState<WeatherData>> get() = _weatherState

    private val _queryState = MutableSharedFlow<QueryState>(replay = 0)
    val queryState: SharedFlow<QueryState> get() = _queryState

    var query = ""
        set(value) {
            getWeatherData(value)
            field = value.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        }

    private fun getWeatherData(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherData = weatherRepo.getWeatherData(city)
                val state = if (weatherData.body() != null) {
                    _queryState.emit(QueryState.Success)
                    ApiState.Success(weatherData.body()!!)
                } else {
                    _queryState.emit(QueryState.Failure)
                    ApiState.Failure("ERROR FETCHING WEATHER DATA")
                }
                _weatherState.postValue(state)
            } catch (error: Exception) {
                _weatherState.postValue(ApiState.Failure("ERROR --> $error"))
            }
        }
    }
}
package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemWeatherBinding
import com.example.weatherapp.model.ApiData
import kotlin.math.roundToInt

class WeatherAdapter(
    private val weatherList: MutableList<ApiData> = mutableListOf(),
    private val listener: (ApiData) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = WeatherViewHolder.getInstance(parent, listener)

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.loadWeather(weatherList[position])
    }

    override fun getItemCount() = weatherList.size

    fun updateList(weather: List<ApiData>) {
        if (weather.lastOrNull() != weatherList.lastOrNull()) {
            val positionStart = weatherList.size
            weatherList.addAll(weather)
            notifyItemRangeInserted(0, positionStart)
        }
    }

    class WeatherViewHolder(
        private val binding: ItemWeatherBinding,
        private val listener: (ApiData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadWeather(weatherData: ApiData) = with(binding) {
            weather.text = weatherData.weather?.firstOrNull()?.main
            "Temp: ${weatherData.main?.temp?.roundToInt().toString()}\u00B0".also { temp.text = it }
            root.setOnClickListener {
                listener.invoke(weatherData)
            }
        }

        companion object {
            fun getInstance(parent: ViewGroup, listener: (ApiData) -> Unit): WeatherViewHolder {
                val binding = ItemWeatherBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return WeatherViewHolder(binding, listener)
            }
        }
    }
}
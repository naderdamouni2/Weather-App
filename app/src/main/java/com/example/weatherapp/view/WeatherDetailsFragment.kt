package com.example.weatherapp.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details) {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: WeatherDetailsFragmentArgs by navArgs()
    private val weatherViewModel by activityViewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWeatherDetailsBinding.inflate(layoutInflater, container, false).also {
        _binding = it

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = weatherViewModel.query

        loadWeatherDetails()
    }

    private fun loadWeatherDetails() = with(binding) {
        "${args.details.main?.temp?.roundToInt().toString()}\u00B0".also { temperature.text = it }
        "Feels like: ${args.details.main?.feelsLike?.roundToInt().toString()}\u00B0".also { feelsLike.text = it }
        weather.text = args.details.weather?.get(0)?.main.toString()
        weatherDescription.text = args.details.weather?.get(0)?.description
    }
}
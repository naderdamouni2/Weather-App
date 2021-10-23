package com.example.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.WeatherAdapter
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.model.ApiData
import com.example.weatherapp.utils.ApiState
import com.example.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherListFragment : Fragment(R.layout.fragment_weather_list) {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!
    private val weatherViewModel by activityViewModels<WeatherViewModel>()
    private val weatherAdapter by lazy { WeatherAdapter(listener = ::getWeatherDetails) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWeatherListBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title = weatherViewModel.query

        setupObservers()
    }

    private fun setupObservers() = with(weatherViewModel) {
        binding.rvList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        weatherState.observe(viewLifecycleOwner) { state ->
            if (state is ApiState.Success) state.data.list?.let { loadWeatherData(it) }
            if (state is ApiState.Failure) handleFailure(state.errorMsg)
        }
    }

    private fun loadWeatherData(weather: List<ApiData>) = with(binding.rvList) {
        if (adapter == null) adapter = weatherAdapter
        weatherAdapter.updateList(weather)
    }

    private fun getWeatherDetails(weather: ApiData) {
        val action = WeatherListFragmentDirections.weatherListFragmentToWeatherDetailsFragment(weather)
        findNavController().navigate(action)
    }

    private fun handleFailure(error: String) {
        Log.d(TAG, "ERROR SEARCHING FOR WEATHER DATA --> $error")
    }

    companion object {
        private const val TAG = "WEATHER_LIST_FRAGMENT"
    }
}
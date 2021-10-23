package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.utils.QueryState
import com.example.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val weatherViewModel by activityViewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).supportActionBar?.title = ""

        handleInputTextListener()
        handleSearchLookUp()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() = with(weatherViewModel) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            queryState.collect { queryState ->
                    when (queryState) {
                        QueryState.Success -> querySuccess()
                        QueryState.Failure -> queryFailure()
                        QueryState.Idle -> {}
                    }
            }
        }
    }

    private fun querySuccess() {
        val action = SearchFragmentDirections.searchFragmentToWeatherListFragment()
        findNavController().navigate(action)
    }

    private fun handleSearchLookUp() = with(binding) {
        searchButton.setOnClickListener {
            weatherViewModel.query = inputText.text.toString()
        }
    }

    private fun handleInputTextListener() = with(binding) {
        inputText.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    inputText.hint = "City Name"
                } else {
                    inputText.hint = ""
                }
            }
    }

    private fun queryFailure() {
        Toast.makeText(context, "Please enter a valid city", Toast.LENGTH_SHORT).show()
    }
}

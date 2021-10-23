package com.example.weatherapp.utils

sealed class QueryState {
    object Idle : QueryState()
    object Success : QueryState()
    object Failure : QueryState()
}

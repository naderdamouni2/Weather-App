package com.example.weatherapp.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherData(
    @Json(name = "city")
    val city: City?,
    @Json(name = "cnt")
    val cnt: Int?,
    @Json(name = "cod")
    val cod: String?,
    @Json(name = "list")
    val list: List<ApiData>?,
    @Json(name = "message")
    val message: Int?
) : Parcelable
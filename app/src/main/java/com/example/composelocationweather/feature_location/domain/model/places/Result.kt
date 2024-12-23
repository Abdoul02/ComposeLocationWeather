package com.example.composelocationweather.feature_location.domain.model.places

data class Result(
    val icon: String,
    val id: String,
    val name: String,
    val opening_hours: OpeningHours? = null,
    val photos: List<Photo>,
    val place_id: String,
    val price_level: Int,
    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,
    val user_ratings_total: Int,
    val vicinity: String
)
package com.example.onlineshop.model

import java.io.Serializable

data class AddressModel(
    val address: String,
    val latitude: Double,
    val longitude: Double
): Serializable
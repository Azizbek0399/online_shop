package com.example.onlineshop.model.request

import java.io.Serializable

data class AddressModel(
    val address:String,
    val latitude: Double,
    val longitude:Double
):Serializable
package com.example.onlineshop.model.request

import com.example.onlineshop.model.CartModel


data class MakeOrderRequest(
    val products: List<CartModel>,
    val order_type: String,
    val adress: String,
    val lat: Double,
    val lon: Double,
    val comment: String
)
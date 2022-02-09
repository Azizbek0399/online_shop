package com.example.onlineshop.model.request

data class RegisterRequest(
    val fullname: String,
    val phone: String,
    val password: String
)
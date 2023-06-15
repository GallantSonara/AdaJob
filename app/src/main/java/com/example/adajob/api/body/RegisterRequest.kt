package com.example.adajob.api.body

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confPassword: String,
)

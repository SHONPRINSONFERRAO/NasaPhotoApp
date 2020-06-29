package com.app.mypicsapp.data.model

data class LoginResponse(
    val status: String,
    val token: String,
    val uniqueId: Long,
    val email: String
)
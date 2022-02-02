package com.pluang.stockapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val isLogin: String,
    val userEmail: String
)
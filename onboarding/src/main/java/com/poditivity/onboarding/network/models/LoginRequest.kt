package com.poditivity.onboarding.network.models

data class LoginRequest(
    val password: String,
    val userName: String
)
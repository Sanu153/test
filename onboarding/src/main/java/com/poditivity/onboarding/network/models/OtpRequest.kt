package com.poditivity.onboarding.network.models

data class OtpRequest(
    val aadharNumber: String,
    val email: String,
    val name: String,
    val phoneNumber: String
)
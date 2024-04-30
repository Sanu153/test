package com.poditivity.onboarding.network.models

data class OtpResponse(
    val otpSent: Boolean
)

data class VerifyOtpResponse(
    val otpValidated: Boolean
)

package com.poditivity.onboarding.network.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val aadharnumber: String,
    val alliescount: Int,
    val email: String,
    val password: String,
    val phonenumber: String,
    val supporterscount: Int,
    val timestamp: String,
    @SerializedName("user_id")
    val userId: Int,
    val username: String
)
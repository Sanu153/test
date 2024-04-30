package com.poditivity.onboarding.network

import com.poditivity.onboarding.network.models.CollegeData
import com.poditivity.onboarding.network.models.CollegeList
import com.poditivity.onboarding.network.models.CourseList
import com.poditivity.onboarding.network.models.ForgetUsernameRequest
import com.poditivity.onboarding.network.models.ForgetUsernameResponse
import com.poditivity.onboarding.network.models.LoginRequest
import com.poditivity.onboarding.network.models.LoginResponse
import com.poditivity.onboarding.network.models.MessageResponse
import com.poditivity.onboarding.network.models.OtpRequest
import com.poditivity.onboarding.network.models.OtpResponse
import com.poditivity.onboarding.network.models.SignUpRequest
import com.poditivity.onboarding.network.models.SignUpResponse
import com.poditivity.onboarding.network.models.VerifyOtpPass
import com.poditivity.onboarding.network.models.VerifyOtpRequest
import com.poditivity.onboarding.network.models.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OnboardingAPI {

    @GET("/api/colleges")
    suspend fun getCollegeData(): Response<CollegeList>

    @GET("/api/college/{id}/degrees")
    suspend fun getCollegeStreams(@Path("id") collegeId: String): Response<CourseList>

    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/signup/otp")
    suspend fun otp(@Body otpRequest: OtpRequest): Response<OtpResponse>

    @POST("/api/auth/signup/otp/verify")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("/api/auth/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @POST("/api/auth/forgot/username/sendmail")
    suspend fun forgetUsername(@Body forgetUsernameRequest: ForgetUsernameRequest): Response<ForgetUsernameResponse>

    @POST("/api/auth/forgot/password/sendmail")
    suspend fun forgetPass(@Body forgetUsernameRequest: ForgetUsernameRequest): Response<ForgetUsernameResponse>


    @POST("/api/auth/forgot/password/verify")
    suspend fun verifyPassOtp(@Body verifyOtpPass: VerifyOtpPass): Response<MessageResponse>
}
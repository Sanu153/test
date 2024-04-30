package com.poditivity.onboarding.network.repository

import com.poditivity.common.Resource
import com.poditivity.common.network.GenericRequest
import com.poditivity.onboarding.network.OnboardingAPI
import com.poditivity.onboarding.network.models.CollegeData
import com.poditivity.onboarding.network.models.CollegeList
import com.poditivity.onboarding.network.models.Course
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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.log

class OnboardingRepository @Inject constructor(
    private val onboardingAPI: OnboardingAPI,
    private val genericRequest: GenericRequest
) {

    suspend fun getCollegeList():Flow<Resource<CollegeList>> = genericRequest.makeApiCall {
        onboardingAPI.getCollegeData()
    }

    suspend fun getCollegeStreams(id: String):Flow<Resource<CourseList>> = genericRequest.makeApiCall {
        onboardingAPI.getCollegeStreams(id)
    }

    suspend fun forgetUsername(forgetUsernameRequest: ForgetUsernameRequest):Flow<Resource<ForgetUsernameResponse>> = genericRequest.makeApiCall {
        onboardingAPI.forgetUsername(forgetUsernameRequest)
    }

    suspend fun forgetPass(forgetUsernameRequest: ForgetUsernameRequest):Flow<Resource<ForgetUsernameResponse>> = genericRequest.makeApiCall {
        onboardingAPI.forgetPass(forgetUsernameRequest)
    }

    suspend fun verifyPass(verifyOtpPass: VerifyOtpPass):Flow<Resource<MessageResponse>> = genericRequest.makeApiCall {
        onboardingAPI.verifyPassOtp(verifyOtpPass)
    }


    suspend fun sendOtp(otpRequest: OtpRequest):Flow<Resource<OtpResponse>> =
        genericRequest.makeApiCall(OtpRequest::class.java) {
        onboardingAPI.otp(otpRequest)
    }

    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest):Flow<Resource<VerifyOtpResponse>> =
        genericRequest.makeApiCall(VerifyOtpRequest::class.java) {
        onboardingAPI.verifyOtp(verifyOtpRequest)
    }

    suspend fun signUp(signUpRequest: SignUpRequest):Flow<Resource<SignUpResponse>> =
        genericRequest.makeApiCall(SignUpRequest::class.java) {
        onboardingAPI.signUp(signUpRequest)
    }

    suspend fun logIn(loginRequest: LoginRequest):Flow<Resource<LoginResponse>> =
        genericRequest.makeApiCall(SignUpRequest::class.java) {
            onboardingAPI.login(loginRequest)
    }

}
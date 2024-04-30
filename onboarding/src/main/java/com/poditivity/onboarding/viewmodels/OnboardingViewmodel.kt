package com.poditivity.onboarding.viewmodels

import androidx.lifecycle.ViewModel
import com.poditivity.onboarding.network.models.ForgetUsernameRequest
import com.poditivity.onboarding.network.models.LoginRequest
import com.poditivity.onboarding.network.models.OtpRequest
import com.poditivity.onboarding.network.models.SignUpRequest
import com.poditivity.onboarding.network.models.VerifyOtpPass
import com.poditivity.onboarding.network.models.VerifyOtpRequest
import com.poditivity.onboarding.network.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OnboardingViewmodel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
): ViewModel() {


    suspend fun getCollegeList() = onboardingRepository.getCollegeList()
    suspend fun getCollegeStreams(id: String) = onboardingRepository.getCollegeStreams(id)

    suspend fun sendOtp(otpRequest: OtpRequest) = onboardingRepository.sendOtp(otpRequest)

    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) = onboardingRepository.verifyOtp(verifyOtpRequest)

    suspend fun signUp(signUpRequest: SignUpRequest) = onboardingRepository.signUp(signUpRequest)

    suspend fun login(loginRequest: LoginRequest) = onboardingRepository.logIn(loginRequest)

    suspend fun forgetUsername(forgetUsernameRequest: ForgetUsernameRequest) =
        onboardingRepository.forgetUsername(forgetUsernameRequest)

    suspend fun forgetPass(forgetUsernameRequest: ForgetUsernameRequest) =
        onboardingRepository.forgetPass(forgetUsernameRequest)

    suspend fun verifyPass(verifyOtpPass: VerifyOtpPass) =
        onboardingRepository.verifyPass(verifyOtpPass)




}
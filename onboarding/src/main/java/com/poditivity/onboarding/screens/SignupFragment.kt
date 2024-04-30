package com.poditivity.onboarding.screens

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.poditivity.common.BaseFragment
import com.poditivity.common.Resource
import com.poditivity.onboarding.R
import com.poditivity.onboarding.databinding.FragmentSignupBinding
import com.poditivity.onboarding.network.models.OtpRequest
import com.poditivity.onboarding.viewmodels.OnboardingViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>() {
    private val viewModel by viewModels<OnboardingViewmodel>()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupBinding {
        return FragmentSignupBinding.inflate(layoutInflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpClickListeners()
        setUpObservers()
        handleBack {
            findNavController().popBackStack()
        }

    }


    override fun setUpViews(){
        binding.email.textField.hint = "Email"
        binding.name.textField.hint = "Name"
        binding.aadhar.textField.hint = "Aadhar Number"
        binding.mobile.textField.hint = "Mobile Number"
        binding.signup.button.text = "OTP"

        binding.aadhar.inputField.inputType = InputType.TYPE_CLASS_NUMBER
        binding.mobile.inputField.inputType = InputType.TYPE_CLASS_NUMBER
        val text = "Already have an account? Login"

        val spannableString = SpannableString(text)

        spannableString.setSpan(UnderlineSpan(), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(com.poditivity.common.R.color.grey)), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(ForegroundColorSpan(Color.BLACK), 0, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.login.text = spannableString

    }

    override fun setUpClickListeners() {
        binding.signup.button.setOnClickListener {

            if(validate()) {
                with(binding){
                    lifecycleScope.launch {
                        viewModel.sendOtp(
                            OtpRequest(
                            aadhar.inputField.text.toString(),
                            email.inputField.text.toString(),
                            name.inputField.text.toString(),
                            mobile.inputField.text.toString()
                            )
                        ).collect{
                            when(it){
                                is Resource.Success -> {
                                    (activity as OnboardingActivity).hideLoading()
                                    if(it.data?.otpSent == true){
                                        showSnackbar("OTP sent")

                                        findNavController().navigate(
                                            R.id.action_signupFragment_to_otpFragment
                                        )
                                    }
                                }
                                is Resource.Error -> {
                                    (activity as OnboardingActivity).hideLoading()
                                    showSnackbar(it.message.toString())
                                }
                                is Resource.Loading -> {
                                    (activity as OnboardingActivity).showLoading()
                                }
                            }

                        }
                    }

                }

            }
        }

        binding.back.root.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }

    override fun setUpObservers() {

    }

    private fun validate(): Boolean{
        with(binding){
            if(aadhar.inputField.text.toString().length != 12){
                showSnackbar("Aadhar number must be of length 12")
                return false
            }

        }

        return true
    }



}
package com.poditivity.onboarding.screens

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.otpview.OTPListener
import com.poditivity.common.BaseFragment
import com.poditivity.onboarding.R
import com.poditivity.onboarding.databinding.FragmentOtpBinding
import com.poditivity.onboarding.network.models.VerifyOtpRequest
import com.poditivity.onboarding.viewmodels.OnboardingViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding>() {

    private val viewModel by viewModels<OnboardingViewmodel>()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOtpBinding {
        return FragmentOtpBinding.inflate(layoutInflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpClickListeners()
        handleBack {
            findNavController().popBackStack()
        }
    }

    override fun setUpViews() {
        binding.signup.button.text = "Step 2/3"
        val text = "Already have an account? Login"

        val spannableString = SpannableString(text)

        spannableString.setSpan(UnderlineSpan(), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(com.poditivity.common.R.color.grey)), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(ForegroundColorSpan(Color.BLACK), 0, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.login.text = spannableString
        binding.otpTextView.requestFocusOTP()
        binding.otpTextView.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                Toast.makeText(requireContext(), "The OTP is $otp", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    viewModel.verifyOtp(
                        VerifyOtpRequest(
                            binding.otpTextView.otp.toString()
                        )
                    ).observeResourceState(
                        this@OtpFragment,
                        {
                            (activity as OnboardingActivity).hideLoading()
                            showSnackbar("OTP verified")
                            findNavController().navigate(R.id.action_otpFragment_to_collegeDetailsFragment)
                        },
                        {
                            (activity as OnboardingActivity).hideLoading()
                            showSnackbar(it)
                        },
                        {
                            (activity as OnboardingActivity).showLoading()
                        }
                    )
                }
            }
        }
    }

    override fun setUpClickListeners() {
        binding.signup.button.setOnClickListener {

        }

        binding.back.root.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setUpObservers() {
        TODO("Not yet implemented")
    }


    fun additionalMethods() {
        with(binding){
            otpTextView.otpListener  // retrieves the current OTPListener (null if nothing is set)
            otpTextView.requestFocusOTP();	//sets the focus to OTP box (does not open the keyboard)
            //otpTextView.setOTP(otpString);	// sets the entered otpString in the Otp box (for case when otp is retrieved from SMS)
            otpTextView.otp	// retrieves the OTP entered by user (works for partial otp input too)
            otpTextView.showSuccess();	// shows the success state to the user (can be set a bar color or drawable)
            otpTextView.showError();	// shows the success state to the user (can be set a bar color or drawable)
            otpTextView.resetState();	// brings the views back to default state (the state it was at input)
        }

    }



}
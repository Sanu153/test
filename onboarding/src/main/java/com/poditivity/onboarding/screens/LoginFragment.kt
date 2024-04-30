package com.poditivity.onboarding.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.poditivity.common.BaseFragment
import com.poditivity.onboarding.R
import com.poditivity.onboarding.databinding.FragmentLoginBinding
import com.poditivity.onboarding.network.models.LoginRequest
import com.poditivity.onboarding.viewmodels.OnboardingViewmodel
import com.poditivity.social.SocialActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel by viewModels<OnboardingViewmodel>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpClickListeners()
        setUpObservers()
    }

    override fun setUpViews() {
        with(binding){
            username.textField.hint = "Username"
            password.textField.hint = "Password"
            login.button.text = "Login"
        }
    }

    override fun setUpClickListeners() {
        binding.login.button.setOnClickListener {
            lifecycleScope.launch {
                viewModel.login(LoginRequest(
                    userName = binding.username.inputField.text.toString(),
                    password = binding.password.inputField.text.toString()
                )).observeResourceState(
                    this@LoginFragment,
                    {
                        (activity as OnboardingActivity).hideLoading()
                        openHomeActivity()
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
        binding.forPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
        }

        binding.forUsename.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetUsernameFragment)
        }

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }






    }

    private fun openHomeActivity(){
        val intent = Intent(requireActivity(), SocialActivity::class.java)
        activity?.finish()
        startActivity(intent)
    }

    override fun setUpObservers() {
    }

}
package com.poditivity.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.poditivity.common.BaseFragment
import com.poditivity.onboarding.R
import com.poditivity.onboarding.databinding.FragmentInfoBinding



class InfoFragment : BaseFragment<FragmentInfoBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInfoBinding {
        return FragmentInfoBinding.inflate(inflater, container, false)
    }

    override fun setUpViews() {

    }

    override fun setUpClickListeners() {

    }

    override fun setUpObservers() {
        binding.get.button.text = "Get started"
        binding.get.button.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_memberSelectorFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

}
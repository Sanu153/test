package com.poditivity.onboarding.screens

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.poditivity.common.BaseFragment
import com.poditivity.onboarding.R
import com.poditivity.onboarding.databinding.FragmentMemberSelectorBinding


class MemberSelectorFragment : BaseFragment<FragmentMemberSelectorBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMemberSelectorBinding {
        return FragmentMemberSelectorBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpClickListeners()
    }
    override fun setUpViews() {
        val colorInt: Int = requireContext().getColor(com.poditivity.common.R.color.primary)
        val csl = ColorStateList.valueOf(colorInt)

        val a: Int = requireContext().getColor(com.poditivity.common.R.color.white)
        val v = ColorStateList.valueOf(a)
        binding.signup.button.text = "Next"
        binding.studentCard.setCardBackgroundColor(csl)
        binding.instCard.setCardBackgroundColor(v)
    }

    override fun setUpClickListeners() {
        binding.signup.button.setOnClickListener {
            findNavController().navigate(R.id.action_memberSelectorFragment_to_signupFragment)
        }
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_memberSelectorFragment_to_loginFragment)
        }
        val colorInt: Int = requireContext().getColor(com.poditivity.common.R.color.primary)
        val csl = ColorStateList.valueOf(colorInt)

        val a: Int = requireContext().getColor(com.poditivity.common.R.color.white)
        val v = ColorStateList.valueOf(a)

        binding.studentCard.setOnClickListener {

            binding.studentCard.setCardBackgroundColor(csl)
            binding.instCard.setCardBackgroundColor(v)
        }

        binding.instCard.setOnClickListener {

            binding.instCard.setCardBackgroundColor(csl)
            binding.studentCard.setCardBackgroundColor(v)
        }
    }

    override fun setUpObservers() {
    }

}
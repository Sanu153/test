package com.poditivity.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow



abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
     val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun handleBack(callback:()->Unit){
        val back = requireActivity().onBackPressedDispatcher.addCallback {
            callback()
        }
        back.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setUpViews()
    abstract fun setUpClickListeners()

    abstract fun setUpObservers()

    fun showSnackbar(message: String){

        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }



    inline fun <reified T> Flow<Resource<T>>.observeResourceState(
        fragment: Fragment,
        crossinline onSuccess: (T) -> Unit,
        crossinline onError: (String) -> Unit,
        crossinline onLoading: () -> Unit
    ) {
        fragment.lifecycleScope.launchWhenStarted {
            collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        onSuccess(resource.data!!)
                    }
                    is Resource.Error -> {
                        onError(resource.message!!)
                    }
                    is Resource.Loading -> {
                        onLoading()
                    }
                }
            }
        }
    }


}
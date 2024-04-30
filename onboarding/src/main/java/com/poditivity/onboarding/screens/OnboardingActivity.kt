package com.poditivity.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.poditivity.onboarding.R
import com.poditivity.social.SocialActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private lateinit var view:LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        view  = findViewById(R.id.loading)

        val prefs = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        if(prefs.getBoolean("IS_LOGGED_IN", false)){
            val intent = Intent(this, SocialActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    fun showLoading(){
        view.isVisible = true
        view.isActivated = true
    }

    fun hideLoading(){
        view.isVisible = false
        view.isActivated = false
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }


}
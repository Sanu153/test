package com.poditivity.social

import android.os.Bundle
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieAnimationView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class
SocialActivity : AppCompatActivity() {
    private lateinit var view: LottieAnimationView
    private lateinit var navGraph: NavGraph
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_)
        view  = findViewById(R.id.loading)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_social) as NavHostFragment

        val navController = navHostFragment.navController

        navGraph = navController.navInflater.inflate(R.navigation.social_nav_graph)
        navGraph.setStartDestination(R.id.homeFragment)


        val bottomNavigation: MeowBottomNavigation = findViewById(R.id.dd)
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.home))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.video))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.add))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.airdrop))
        bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.user))

        bottomNavigation.show(1)

        bottomNavigation.setOnShowListener {it
            when (it.id) {
                1 -> {}
                2 -> {}
                3 -> {}
                4 -> {}
                5 -> {}
            }
            return@setOnShowListener
        }

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> {
                    navController.navigate(R.id.homeFragment)
                }
                2 -> navGraph.setStartDestination(R.id.profileFragment)
                3 -> navController.navigate(R.id.createPostFragment)
                4 -> navGraph.setStartDestination(R.id.profileFragment)
                5 -> {
                    navController.navigate(R.id.profileFragment)
                }
            }
            return@setOnClickMenuListener
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
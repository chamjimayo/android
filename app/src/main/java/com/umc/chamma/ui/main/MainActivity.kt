package com.umc.chamma.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityMainBinding
import com.umc.chamma.ui.home.main.HomeFragment
import com.umc.chamma.ui.login.model.LoginResponseData
import com.umc.chamma.ui.mypage.mypage.MypageFragment
import com.umc.chamma.ui.mypage.review.ReviewFragment
import com.umc.chamma.ui.mypage.usage.UsageFragment
import com.umc.chamma.ui.mypage.userdata.UpdateUserData
import com.umc.chamma.ui.toiletlist.ToiletlistFragment
import com.umc.chamma.util.Constants
import com.umc.chamma.util.InappUtil

class MainActivity : BaseActivityVB<ActivityMainBinding>(ActivityMainBinding::inflate) {


    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNV.itemIconTintList = null
        setFullScreen()
        InappUtil.initBillingClient(this)
        setBottomNavigation()
    }


    private fun setBottomNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame) as NavHostFragment
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNV)
            .setupWithNavController(navController)

        binding.bottomNV.setOnItemSelectedListener { item->
            if(binding.bottomNV.selectedItemId != item.itemId){
                NavigationUI.onNavDestinationSelected(item,navController,false)
            }
            true
        }
    }

    // 풀스크린 적용
    private fun setFullScreen(){
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}
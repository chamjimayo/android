package com.android.chamma.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.android.chamma.R
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivityMainBinding
import com.android.chamma.ui.community.CommunityFragment
import com.android.chamma.ui.home.HomeFragment
import com.android.chamma.ui.mypage.MypageFragment
import com.android.chamma.ui.toiletlist.ToiletlistFragment

class MainActivity : BaseActivityVB<ActivityMainBinding>(ActivityMainBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNV.itemIconTintList = null
        setFullScreen()
        setBottomNavigation()
    }


    private fun setBottomNavigation(){
        binding.bottomNV.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, HomeFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                    R.id.navigation_toilets -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, ToiletlistFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }

                    R.id.navigation_community -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, CommunityFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }

                    R.id.navigation_mypage -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, MypageFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }

                }
                true
            }
            selectedItemId = R.id.navigation_home
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
package com.android.chamma.ui.main

import android.os.Bundle
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
}
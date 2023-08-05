package com.umc.chamma.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.umc.chamma.R
import com.umc.chamma.databinding.ActivityMainBinding
import com.umc.chamma.ui.home.main.HomeFragment
import com.umc.chamma.ui.mypage.*
import com.umc.chamma.ui.toiletlist.ToiletlistFragment
import com.umc.chamma.util.InappUtil

class MainActivity : com.umc.chamma.config.BaseActivityVB<ActivityMainBinding>(ActivityMainBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNV.itemIconTintList = null
        setFullScreen()
        setBottomNavigation()
        InappUtil.initBillingClient(this)
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
                            .replace(R.id.frame, com.umc.chamma.ui.community.CommunityFragment())
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






    //mypage에서 다른 화면으로 이동
    fun mypageToUsage() {
        val usageFragment = UsageFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,usageFragment )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun mypageToCharge() {
        val chargePointFragment = ChargePointFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,chargePointFragment )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun mypageToUpdate() {
        val updatdeFragment = UpdateUserData()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,updatdeFragment )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun mypageToReview() {
        val reviewFragment = ReviewFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,reviewFragment )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun goBackMypage() {
        onBackPressed()
    }



}
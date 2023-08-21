package com.umc.chamma.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityMainBinding
import com.umc.chamma.ui.community.CommunityFragment
import com.umc.chamma.ui.home.main.HomeFragment
import com.umc.chamma.ui.mypage.chargepoint.ChargePointActivity
import com.umc.chamma.ui.mypage.mypage.MypageFragment
import com.umc.chamma.ui.mypage.review.ReviewFragment
import com.umc.chamma.ui.mypage.usage.UsageFragment
import com.umc.chamma.ui.mypage.userdata.UpdateUserData
import com.umc.chamma.ui.toiletlist.ToiletlistFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umc.chamma.R
import com.umc.chamma.config.App.Companion.sharedPreferences
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityMainBinding
import com.umc.chamma.ui.reviewwrite.ReviewWriteActivity
import com.umc.chamma.ui.using.UsingActivity
import com.umc.chamma.util.Constants.IS_USING
import com.umc.chamma.util.Constants.TAG
import com.umc.chamma.util.InappUtil

class MainActivity : BaseActivityVB<ActivityMainBinding>(ActivityMainBinding::inflate) {


    private lateinit var navController : NavController
    private val usingState by lazy{sharedPreferences.getBoolean(IS_USING,false)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG,usingState.toString())
        if(usingState){
            val intent = Intent(this,UsingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        binding.bottomNV.itemIconTintList = null
        setFullScreen()
        InappUtil.initBillingClient(this)
        setBottomNavigation()

        if(intent.hasExtra("ID")){
            val restroomId = intent.getIntExtra("ID",0)
            showTitleTwoButtonDialog(this,"이용은 만족스러웠나요?",
            "더 나은 화장실 이용을 위해 리뷰를 남겨주세요",
            "괜찮아요","리뷰쓰기",{dismissTitleTwoButtonDialog()}){
                val intent = Intent(this,ReviewWriteActivity::class.java)
                    .putExtra("ID",restroomId)
                startActivity(intent)
            }
        }
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







    //mypage에서 다른 화면으로 이동
    fun mypageToUsage() {
        val usageFragment = UsageFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,usageFragment )
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun mypageToUpdate() {
        val updateFragment = UpdateUserData()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,updateFragment )
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

    fun hideBottomNavigation(bool: Boolean){
       val bottomNavigation = binding.bottomNV

        if(bool == true){
            bottomNavigation.visibility = View.VISIBLE
        }

        if(bool == false){
            bottomNavigation.visibility = View.INVISIBLE
        }

    }


}
package com.umc.chamma.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityMainBinding
import com.umc.chamma.ui.reviewwrite.ReviewWriteActivity
import com.umc.chamma.util.InappUtil
import com.umc.chamma.util.RestroomNotification

class MainActivity : BaseActivityVB<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNV.itemIconTintList = null
        setFullScreen()
        InappUtil.initBillingClient(this)
        setBottomNavigation()

        if (intent.hasExtra("ID")) {
            val restroomId = intent.getIntExtra("ID", 0)
            showTitleTwoButtonDialog(this, "이용은 만족스러웠나요?",
                "더 나은 화장실 이용을 위해 리뷰를 남겨주세요",
                "괜찮아요", "리뷰쓰기", { dismissTitleTwoButtonDialog() }) {
                val intent = Intent(this, ReviewWriteActivity::class.java)
                    .putExtra("ID", restroomId)
                startActivity(intent)
            }
        }
    }
/*
    override fun onPause() {
        RestroomNotification(this).createNotification()
        super.onPause()
    }


 */

    override fun onResume() {
        super.onResume()
        RestroomNotification(this).removeNotification()
    }



    private fun setBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame) as NavHostFragment
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNV)
            .setupWithNavController(navController)

        binding.bottomNV.setOnItemSelectedListener { item ->
            if (binding.bottomNV.selectedItemId != item.itemId) {
                NavigationUI.onNavDestinationSelected(item, navController, false)
            }
            true
        }
    }

    // 풀스크린 적용
    private fun setFullScreen() {
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

}
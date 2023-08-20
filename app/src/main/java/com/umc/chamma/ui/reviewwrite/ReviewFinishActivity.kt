package com.umc.chamma.ui.reviewwrite

import android.content.Intent
import android.os.Bundle
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityReviewfinishBinding
import com.umc.chamma.ui.main.MainActivity

class ReviewFinishActivity : BaseActivityVB<ActivityReviewfinishBinding>(ActivityReviewfinishBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.closeBtn.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
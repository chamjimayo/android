package com.umc.chamma.ui.reviewwrite

import android.content.Intent
import android.os.Bundle
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityReviewwriteBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.using.CurRestrom

class ReviewWriteActivity : BaseActivityVB<ActivityReviewwriteBinding>(ActivityReviewwriteBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val restroomId = intent.getIntExtra("ID",0)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.review_frame, ReviewFirstFragment(restroomId))
            .commit()

    }


}
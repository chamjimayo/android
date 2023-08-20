package com.umc.chamma.ui.reviewwrite

import android.os.Bundle
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityReviewwriteBinding

class ReviewWriteActivity : BaseActivityVB<ActivityReviewwriteBinding>(ActivityReviewwriteBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.review_frame, ReviewFirstFragment())
            .commit()

    }


}
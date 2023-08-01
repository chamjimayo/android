package com.android.chamma.ui.toiletlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.chamma.R
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivityReviewBinding

class ReviewActivity : BaseActivityVB<ActivityReviewBinding>(ActivityReviewBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.optionBtn.setOnClickListener{
            
        }
    }
}
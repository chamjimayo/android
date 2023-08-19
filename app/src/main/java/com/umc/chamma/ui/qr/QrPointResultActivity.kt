package com.umc.chamma.ui.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityQrPointResultBinding

class QrPointResultActivity : BaseActivityVB<ActivityQrPointResultBinding>(ActivityQrPointResultBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.closeBtn.setOnClickListener {
            finish()
        }
    }
}
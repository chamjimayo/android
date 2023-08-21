package com.umc.chamma.ui.qr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityQrPointResultBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.using.UsingActivity

class QrPointResultActivity : BaseActivityVB<ActivityQrPointResultBinding>(ActivityQrPointResultBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.closeBtn.setOnClickListener {
            val intent = Intent(this, UsingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
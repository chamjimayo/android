package com.umc.chamma.ui.using

import android.content.Intent
import android.os.Bundle
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityUsingBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.using.CurRestrom.restroomId

class UsingActivity : BaseActivityVB<ActivityUsingBinding>(ActivityUsingBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restroomId = intent.getIntExtra("ID",0)

        binding.close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
                .putExtra("ID",restroomId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.game.setOnClickListener {
            val intent = Intent(this,LobbyActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {}

}
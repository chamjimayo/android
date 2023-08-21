package com.umc.chamma.ui.using

import android.content.Intent
import android.os.Bundle
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityUsingBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.using.model.EndUseResponseData

class UsingActivity : BaseActivityVB<ActivityUsingBinding>(ActivityUsingBinding::inflate),EnduseInterface{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.close.setOnClickListener {
            EnduseService(this).postEnduse()
        }

        binding.game.setOnClickListener {
            val intent = Intent(this,LobbyActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {}

    override fun onPostenduseSuccess(data: EndUseResponseData) {
        val intent = Intent(this, MainActivity::class.java)
            .putExtra("ID",data.restroomId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onPostenduseFailure(message: String) {
        showCustomToast(message)
    }

}
package com.umc.chamma.ui.using

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.umc.chamma.R
import com.umc.chamma.databinding.ActivityLobbyBinding

class LobbyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLobbyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        binding = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val img = binding.ivMainHeader
        val btn = binding.btnMainStart

        Glide.with(this)
            .load(R.drawable.gameheader)
            .into(img)

        btn.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        setFullScreen()

    }

    private fun setFullScreen(){
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}
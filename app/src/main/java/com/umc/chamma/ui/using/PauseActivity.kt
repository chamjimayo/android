package com.umc.chamma.ui.using

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.chamma.databinding.ActivityPauseBinding

class PauseActivity : AppCompatActivity(){
    private lateinit var binding : ActivityPauseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPauseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resumebtn = binding.btnPauseResume
        val exitbtn = binding.btnPauseExit

        resumebtn.setOnClickListener {
            finish()
        }

        exitbtn.setOnClickListener {
            val intent = Intent(this, LobbyActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
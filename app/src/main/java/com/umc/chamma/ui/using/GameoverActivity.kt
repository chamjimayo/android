
package com.umc.chamma.ui.using

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.chamma.databinding.ActivityGameoverBinding

class GameoverActivity : AppCompatActivity(){
    private lateinit var binding : ActivityGameoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameoverBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val exit = binding.btnGameoverExit
        val retry = binding.btnGameoverRetry

        binding.tvScore.text = "Score : " + intent.getIntExtra("SCORE",0).toString()

        exit.setOnClickListener {
            val intent = Intent(this, UsingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        retry.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
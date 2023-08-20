

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.chamma.databinding.ActivityGameoverBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.using.GameActivity

class GameoverActivity : AppCompatActivity(){
    private lateinit var binding : ActivityGameoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameoverBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val exit = binding.btnGameoverExit
        val retry = binding.btnGameoverRetry

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        retry.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
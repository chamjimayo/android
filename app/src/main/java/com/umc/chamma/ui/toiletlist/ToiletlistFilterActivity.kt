package com.umc.chamma.ui.toiletlist

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.App.Companion.sharedPreferences
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityToiletlistFilterBinding
import com.umc.chamma.util.Constants.DISTANCE_FILTER


class ToiletlistFilterActivity : BaseActivityVB<ActivityToiletlistFilterBinding>(ActivityToiletlistFilterBinding::inflate)  {

    private val distance by lazy{ sharedPreferences.getInt(DISTANCE_FILTER,4) }
    private lateinit var distanceTextArr : MutableList<TextView>
    private var curProgress = 0
    private var changeState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        distanceTextArr = mutableListOf(binding.tvRange1,binding.tvRange2,binding.tvRange3,binding.tvRange4,binding.tvRange5)
        
        // TODO 초기화
        binding.distanceSeekbar.progress = distance
        curProgress = distance 
        distanceTextArr[distance].setTextColor(ContextCompat.getColor(App.context(), R.color.black))


        binding.distanceSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                changeState = true
                curProgress = progress
                distanceTextArr.forEach{it.setTextColor(ContextCompat.getColor(App.context(), R.color.chamma_signup_strokegray))}
                distanceTextArr[progress].setTextColor(ContextCompat.getColor(App.context(), R.color.black))

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        binding.btnStore.setOnClickListener {
            sharedPreferences.edit()
                .putInt(DISTANCE_FILTER,curProgress)
                .apply()

            finish()
        }

        binding.btnBack.setOnClickListener {
            if(changeState){
                showTitleTwoButtonDialog(this,"변경된 내용이 있어요!","이 페이지에서 나가면 변경 사항이 저장되지 않아요.", "괜찮아요", "저장하기",
                    {finish()}){
                    sharedPreferences.edit()
                        .putInt(DISTANCE_FILTER,curProgress)
                        .apply()

                    finish()
                }
            }else{
                finish()
            }
        }

    }


    override fun onBackPressed() {
        if(changeState){
            showTitleTwoButtonDialog(this,"변경된 내용이 있어요!","이 페이지에서 나가면 변경 사항이 저장되지 않아요.", "괜찮아요", "저장하기",
                {finish()}){
                sharedPreferences.edit()
                    .putInt(DISTANCE_FILTER,curProgress)
                    .apply()

                finish()
            }
        }else{
            finish()
        }
    }

}
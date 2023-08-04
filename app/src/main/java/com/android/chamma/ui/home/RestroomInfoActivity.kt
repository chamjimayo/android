package com.android.chamma.ui.home

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.android.chamma.R
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivityRestroomInfoBinding
import com.android.chamma.databinding.ActivityReviewBinding
import com.android.chamma.ui.qr.QRActivity
import me.relex.circleindicator.CircleIndicator3

class RestroomInfoActivity : BaseActivityVB<ActivityRestroomInfoBinding>(ActivityRestroomInfoBinding::inflate){
    private var pageItemList = ArrayList<Int>()
    private lateinit var RestroomVPAdapter: RestroomVPAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_restroom_info)

        try {
            Log.d("왜", "안돼")
            pageItemList.add(R.drawable.restroom_ex)
            pageItemList.add(R.drawable.restroom_ex)
            pageItemList.add(R.drawable.restroom_ex)
            pageItemList.add(R.drawable.restroom_ex)
            Log.d("왜", "안돼")
            //Log.e("왜",error.toString())

            RestroomVPAdapter = RestroomVPAdapter(pageItemList)
            Log.d("왜", "안돼3")

            binding.restroomVp.apply {
                adapter = RestroomVPAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            Log.d("왜", "안돼4")

            val indicator: CircleIndicator3 = binding.indicator
            indicator.setViewPager(binding.restroomVp)
            Log.d("왜", "안돼5")

            binding.reviewTv.setOnClickListener {
                val intent = Intent(this, ReviewActivity::class.java)
                startActivity(intent)
            }
            Log.d("왜", "안돼6")

            binding.reviewTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            binding.toolBar.setNavigationOnClickListener {
                finish()
            }

            binding.useBtn.setOnClickListener {
                startActivity(Intent(this, QRActivity::class.java))

            }

        } catch (e: Exception) {
            Log.d("왜",e.message.toString())
        }
    }
}
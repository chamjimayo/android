package com.umc.chamma.ui.home.restroomInfo

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
//import com.android.chamma.R
//import com.android.chamma.R
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityRestroomInfoBinding
import com.umc.chamma.ui.home.model.RestroomDetailResponse
import com.umc.chamma.ui.qr.QRActivity
import me.relex.circleindicator.CircleIndicator3

class RestroomInfoActivity : BaseActivityVB<ActivityRestroomInfoBinding>(ActivityRestroomInfoBinding::inflate)
    ,RestroomInfoActivityInterface{
    private var pageItemList = ArrayList<Int>()
    private lateinit var RestroomVPAdapter: RestroomVPAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_restroom_info)

        //풀스크린-MainActivity
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        pageItemList.add(R.drawable.restroom_ex)
        pageItemList.add(R.drawable.restroom_ex)
        pageItemList.add(R.drawable.restroom_ex)
        pageItemList.add(R.drawable.restroom_ex)
        //Log.e("왜",error.toString())

        RestroomVPAdapter = RestroomVPAdapter(pageItemList)

        binding.restroomVp.apply {
            adapter = RestroomVPAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        val indicator: CircleIndicator3 = binding.indicator
        indicator.setViewPager(binding.restroomVp)

        binding.reviewTv.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            startActivity(intent)
        }

        binding.reviewTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.toolBar.setOnClickListener {
            finish()
        }

        binding.useBtn.setOnClickListener {
            startActivity(Intent(this, QRActivity::class.java))

        }

        RestroomInfoService(this).tryToGetRestroomDetail(1)
    }

    override fun onTryToGetRDSuccess(response: RestroomDetailResponse){
        Log.d("연결결과",response.toString())
    }
    /*
    2023-08-05 23:19:12.502 19173-19173 연결결과
    com.umc.chamma
    D  RestroomDetailResponse(data=RDResult(restroomName=참지마요Test_1, longitude=126.9525692, latitude=37.5453949,
    unisex=true, address=서울 마포구 마포대로 122, operatingHour=24시, restroomPhoto=photoUrl,
    equipmentExistenceProbability=0, publicOrPaid=paid, accessibleToiletExistence=true,
    maleToiletCount=5, femaleToiletCount=10, availableMaleToiletCount=5, availableFemaleToiletCount=10,
    equipements=[], reviews=[reviewList(reviewContent=좋아요!, id=1), reviewList(reviewContent=나쁘지않아요!, id=2),
    reviewList(reviewContent=굿!, id=3), reviewList(reviewContent=최악..., id=4)], restroomManager=null))

     */

    override fun onTryToGetRDFailure(message:String){
        Log.d("연결결과",message)
    }
}
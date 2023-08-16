package com.umc.chamma.ui.home.restroomInfo

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.naver.maps.map.a.g
//import com.android.chamma.R
//import com.android.chamma.R
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityRestroomInfoBinding
import com.umc.chamma.databinding.LayoutRestroomViewpagerItemBinding
import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import com.umc.chamma.ui.qr.QRActivity
import me.relex.circleindicator.CircleIndicator3

class RestroomInfoActivity : BaseActivityVB<ActivityRestroomInfoBinding>(ActivityRestroomInfoBinding::inflate)
    ,RestroomInfoActivityInterface{
    private var pageItemList = ArrayList<String>()
    private lateinit var RestroomVPAdapter: RestroomVPAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val Id= intent.getIntExtra("ID",0)
        Log.d("연결결과 ",Id.toString())

        //풀스크린-MainActivity
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
       // val dummyUrl:String="https://drive.google.com/file/d/1ysivvAGJECyi14ImyaS-R2F1K2H0onCI/view?usp=sharing"
        //pageItemList.add(dummyUrl)
        //pageItemList.add(dummyUrl)
        //pageItemList.add(dummyUrl)
        //pageItemList.add(dummyUrl)

        //val binding2:LayoutRestroomViewpagerItemBinding=LayoutRestroomViewpagerItemBinding.inflate(layoutInflater)
        //Glide.with(this).load(dummyUrl).into(binding2.pagerItemImage)


        //Log.e("왜",error.toString())

       // RestroomVPAdapter = RestroomVPAdapter(pageItemList,this)
        /*
        binding.restroomVp.apply {
            adapter = RestroomVPAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
         */
        //val indicator: CircleIndicator3 = binding.indicator
        //indicator.setViewPager(binding.restroomVp)


        binding.reviewTv.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
                .putExtra("ID",Id)
            startActivity(intent)
        }

        binding.reviewTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.toolBar.setOnClickListener {
            finish()
        }

        binding.useBtn.setOnClickListener {
            startActivity(Intent(this, QRActivity::class.java))

        }

        RestroomInfoService(this).tryToGetRestroomDetail(Id)
    }

    override fun onTryToGetRDSuccess(response: RestroomDetailResponse){
        Log.d("연결결과",response.toString())
        val data=response.data

        binding.restroomTv.text=data.restroomName

        val probabilityTv:String="비품이 있을 확률 ${data.equipmentExistenceProbability}%"
        binding.probabilityTv.text=probabilityTv


        binding.toiletNumTv.text= spannableText(data.maleToiletCount.toString())
        //api 정보 없음
        binding.toilet2NumTv.text=spannableText("4")
        binding.toilet3NumTv.text=spannableText("4")
        binding.washstandNumTv.text=spannableText("4")
        binding.mirrorNumTv.text=spannableText("4")


        binding.toiletWNumTv.text= spannableText(data.femaleToiletCount.toString())
        //api 정보 없음
        binding.toilet2WNumTv.text=spannableText("4")
        binding.washstandWNumTv.text=spannableText("4")
        binding.mirrorWNumTv.text=spannableText("4")


        binding.ratingBar.rating=data.averageRating
        binding.starNumTv.text=data.averageRating.toString()

        RestroomVPAdapter=RestroomVPAdapter(data.restroomPhoto,this)
        binding.restroomVp.apply {
            adapter = RestroomVPAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        initIndicator()
    }

    fun initIndicator(){
        val pager = binding.restroomVp
        val indicator = binding.pageIndicatorView

        indicator.count=RestroomVPAdapter.itemCount
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.selection = position
            }
        })
    }

    fun spannableText(data:String):SpannableStringBuilder{
        val textData:String="${data}개"
        val spannable=SpannableStringBuilder(textData)
        spannable.setSpan(StyleSpan(Typeface.BOLD),0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannable
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

 RestroomDetailResponse(data=RDResult(restroomName=참지마요Test_6, longitude=126.976034, latitude=37.5632597,
 unisex=true, address=서울 중구 서소문로 134-7, operatingHour=24시,
 restroomPhoto=[https://drive.google.com/file/d/1ysivvAGJECyi14ImyaS-R2F1K2H0onCI/view?usp=sharing,
 https://drive.google.com/file/d/1rSipTyReWiEr20FeprgGuXm_Brr1oIxv/view?usp=sharing],
 equipmentExistenceProbability=0, publicOrPaid=paid, accessibleToiletExistence=true, maleToiletCount=3,
 femaleToiletCount=5, availableMaleToiletCount=3, availableFemaleToiletCount=5, equipements=[], reviews=[],
 restroomManager=null, averageRating=0.0))


     */

    override fun onTryToGetRDFailure(message:String){
        Log.d("연결결과",message)
    }
}
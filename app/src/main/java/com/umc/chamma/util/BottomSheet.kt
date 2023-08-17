package com.umc.chamma.util

import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.databinding.ActivityReviewBinding
import com.umc.chamma.databinding.FragmentBtmshtdialogSortListBinding
import com.umc.chamma.databinding.FragmentHomeBottomsheetBinding
import com.umc.chamma.databinding.FragmentToiletlistBottomsheetBinding
import com.umc.chamma.ui.home.model.NearToiletData
import com.umc.chamma.ui.home.restroomInfo.ReviewService
import kotlin.math.roundToInt

object BottomSheet {


    fun homeToiletInfo(context : Context, data : NearToiletData, onBtnClickListener : () -> Unit, onTextClickListener : (Int) -> Unit) : BottomSheetDialog {
        val dialog = BottomSheetDialog(context)
        val binding = FragmentHomeBottomsheetBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        binding.tvName.text = data.restroomName
        binding.tvDistance.text = "내 위치로부터 ${data.distance?.toInt()}m"
        binding.tvRating.text = ((data.reviewRating!! * 100.0).roundToInt() / 100.0).toString()
        if(data.publicOrPaid == "public") binding.tvType.text = "무료화장실"
        else binding.tvType.text = "유료화장실"
        binding.ratingbar.rating = data.reviewRating

        binding.btnUse.setOnClickListener { onBtnClickListener() }
        binding.tvName.setOnClickListener { onTextClickListener(data.restroomId!!) }

        return dialog
    }
    
    fun toiletlistSort(context : Context, curPosition : Int, onClickListener : (Int) -> Unit) : BottomSheetDialog{
        val dialog = BottomSheetDialog(context)
        val binding = FragmentToiletlistBottomsheetBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        when(curPosition){
            0-> binding.btnDistance.setTextColor(ContextCompat.getColor(App.context(), R.color.selector_text_btmsht_click))
            1-> binding.btnRatingDesc.setTextColor(ContextCompat.getColor(App.context(), R.color.selector_text_btmsht_click))
            2-> binding.btnRatingAsc.setTextColor(ContextCompat.getColor(App.context(), R.color.selector_text_btmsht_click))
        }
        binding.btnDistance.setOnClickListener {
            onClickListener(0)
            dialog.dismiss()
        }
        binding.btnRatingDesc.setOnClickListener {
            onClickListener(1)
            dialog.dismiss()
        }
        binding.btnRatingAsc.setOnClickListener {
            onClickListener(2)
            dialog.dismiss()
        }
        binding.btmshtBtnClose.setOnClickListener { dialog.dismiss() }


        return dialog
    }

    fun reviewSequence(context : Context,subBinding: ActivityReviewBinding,service:ReviewService,Id:Int) {
        val dialog = BottomSheetDialog(context)
        val binding = FragmentBtmshtdialogSortListBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        dialog.show()

        binding.newestTv.setOnClickListener {
            subBinding.optionBtn.text="최신순"
            service.tryToGetReviewListLatest(Id)
        }
        binding.highestTv.setOnClickListener {
            subBinding.optionBtn.text="별점 높은 순"
            service.tryToGetReviewListHR(Id)


        }
        binding.lowTv.setOnClickListener {
            subBinding.optionBtn.text="별점 낮은 순"
            service.tryToGetReviewListLR(Id)

        }
        binding.btmshtBtnClose.setOnClickListener {
            dialog.dismiss()
        }
    }
}
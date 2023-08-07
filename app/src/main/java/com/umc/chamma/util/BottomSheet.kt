package com.umc.chamma.util

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umc.chamma.databinding.FragmentHomeBottomsheetBinding
import com.umc.chamma.models.homemodel.MarkerData

object BottomSheet {


    fun homeToiletInfo(context : Context, data : MarkerData, onBtnClickListener : () -> Unit, onTextClickListener : (Int) -> Unit) : BottomSheetDialog {
        val dialog = BottomSheetDialog(context)
        val binding = FragmentHomeBottomsheetBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        binding.tvName.text = data.restroomName
        binding.tvDistance.text = "내 위치로부터 ${data.distance?.toInt()}m"
        binding.tvRating.text = data.reviewRating.toString()
        if(data.publicOrPaid == "public") binding.tvType.text = "무료화장실"
        else binding.tvType.text = "유료화장실"
        binding.ratingbar.rating = data.reviewRating!!

        binding.btnUse.setOnClickListener { onBtnClickListener() }
        binding.tvName.setOnClickListener { onTextClickListener(data.restroomId!!) }

        return dialog
    }
    
    // TODO 위에거랑 똑같이 함수로 만들어서, 인자 넣은다음 .show()로 호출만 해주면 됨
     



}
package com.android.chamma.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.android.chamma.databinding.FragmentHomeBottomsheetBinding
import com.android.chamma.models.homemodel.MarkerData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeBottomSheet(val data : MarkerData) : BottomSheetDialogFragment() {

    private var _binding : FragmentHomeBottomsheetBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = data.restroomName
        binding.tvDistance.text = "내 위치로부터 ${data.distance.toInt()}m"
        binding.tvRating.text = data.rating.toString()
        binding.tvType.text = data.publicOrPaid
        binding.ratingbar.rating = data.rating
    }


}
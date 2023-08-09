package com.umc.chamma.ui.mypage.chargepoint

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.chamma.R
import com.umc.chamma.databinding.FragmentChargePointBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.util.InappUtil


class ChargePointFragment : Fragment(R.layout.fragment_charge_point) {

    lateinit var mainActivity: MainActivity




    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentChargePointBinding.inflate(inflater, container, false)

        binding.btn1000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_1000") }
        binding.btn3000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_3000") }
        binding.btn5000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_5000") }
        binding.btn8000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_8000")  }
        binding.btn10000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_10000") }
        binding.btnBackCharge.setOnClickListener { mainActivity.goBackMypage() }

        return binding.root
    }



}
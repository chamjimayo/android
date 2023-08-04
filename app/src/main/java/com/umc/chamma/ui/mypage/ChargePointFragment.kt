package com.umc.chamma.ui.mypage

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
import com.umc.chamma.config.App
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
        binding.btn3000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_3000")}
        binding.btn5000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_5000") }
        binding.btn8000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_8000") }
        binding.btn10000Charge.setOnClickListener { InappUtil.getPay(activity as MainActivity,"point_10000") }
        binding.btnBackCharge.setOnClickListener { mainActivity.goBackMypage() }

        return binding.root
    }









    //인앱 결제 나중에 찾아서 구현하기

//    private fun printDialog(numer:Int){
//        AlertDialog.Builder(mainActivity)
//            .setTitle("알림")
//            .setMessage("${numer}원의 비용이 발생합니다. 그래도 진행하시겠습니까?")
//            .setPositiveButton("동의합니다", object: DialogInterface.OnClickListener{
//                override fun onClick(dialog: DialogInterface?, which: Int) {
//                    charge(numer)
//                }
//            })
//            .setNegativeButton("아니요",object :DialogInterface.OnClickListener{
//                override fun onClick(dialog: DialogInterface?, which: Int) {
//                    Log.d("charge", "stop process")
//                }
//            })
//            .create()
//            .show()
//    }
//
//    fun charge(point: Int){
//
//
//        if(false){
//
//            Log.d("charge", "failed charge point")
//            return
//        }
//        Log.d("charge", "success charge point")
//    }
//
//    private fun charge1 () {
//
//        Log.d("charge", "start process charge 1,000 point")
//       printDialog(1000)
//    }
//
//
//    fun charge3() {
//
//        Log.d("charge", "charge 3,000 point")
//        InappUtil.getPay(activity as MainActivity,"point_3000")
//        printDialog(3000)
//    }
//
//    fun charge5() {
//
//        Log.d("charge", "charge 5,000 point")
//        printDialog(5000)
//    }
//
//    fun charge8() {
//
//        Log.d("charge", "charge 8,000 point")
//        printDialog(8000)
//    }
//
//    fun charge10() {
//
//        Log.d("charge", "charge 10,000 point")
//        printDialog(10000)
//    }

}
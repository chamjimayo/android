package com.umc.chamma.ui.mypage.chargepoint

import android.os.Bundle
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityChargepointBinding
import com.umc.chamma.ui.mypage.chargepoint.model.ChargePointResult
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData
import com.umc.chamma.util.InappUtil


class ChargePointActivity : BaseActivityVB<ActivityChargepointBinding>(ActivityChargepointBinding::inflate), ChargePointActivityInterface {


    private var inappUtil : InappUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inappUtil = InappUtil(this)
        inappUtil?.initBillingClient(this)
        setBtnListener()
        setUserPoint()

    }

    fun setUserPoint(){
        ChargePointService(this).getUserInfo()
    }

    private fun setBtnListener(){

        binding.btn1000Charge.setOnClickListener { inappUtil?.getPay(this,"point_1000") }
        binding.btn3000Charge.setOnClickListener { inappUtil?.getPay(this,"point_3000") }
        binding.btn5000Charge.setOnClickListener { inappUtil?.getPay(this,"point_5000") }
        binding.btn8000Charge.setOnClickListener { inappUtil?.getPay(this,"point_8000")  }
        binding.btn10000Charge.setOnClickListener { inappUtil?.getPay(this,"point_10000") }
        binding.btnBackCharge.setOnClickListener { finish() }
    }


    override fun onGetUserInfoSuccess(data: UserinfoData) {
        binding.tvPoint.text = data.point.toString()
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPause() {
        super.onPause()
        inappUtil = null
    }

    override fun onStop() {
        super.onStop()
        inappUtil = null
    }

    override fun onDestroy() {
        super.onDestroy()
        inappUtil = null
    }


}
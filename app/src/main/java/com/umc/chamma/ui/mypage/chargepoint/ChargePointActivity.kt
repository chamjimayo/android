package com.umc.chamma.ui.mypage.chargepoint

import android.os.Bundle
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityChargepointBinding
import com.umc.chamma.util.InappUtil


class ChargePointActivity : BaseActivityVB<ActivityChargepointBinding>(ActivityChargepointBinding::inflate) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btn1000Charge.setOnClickListener { InappUtil.getPay(this,"point_1000") }
        binding.btn3000Charge.setOnClickListener { InappUtil.getPay(this,"point_3000") }
        binding.btn5000Charge.setOnClickListener { InappUtil.getPay(this,"point_5000") }
        binding.btn8000Charge.setOnClickListener { InappUtil.getPay(this,"point_8000")  }
        binding.btn10000Charge.setOnClickListener { InappUtil.getPay(this,"point_10000") }
        binding.btnBackCharge.setOnClickListener { finish() }

    }



}
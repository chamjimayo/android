package com.umc.chamma.ui.mypage.chargepoint

import android.os.Bundle
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityChargepointBinding
import com.umc.chamma.ui.mypage.chargepoint.model.ChargePointPostData
import com.umc.chamma.ui.mypage.chargepoint.model.ChargePointResponse
import com.umc.chamma.ui.mypage.chargepoint.model.ForceChargePointPostData
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData
import com.umc.chamma.util.InappUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChargePointActivity : BaseActivityVB<ActivityChargepointBinding>(ActivityChargepointBinding::inflate), GetUserinfoInterface, InappInterface {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InappUtil.setinappInterface(this)
        setBtnListener()
        setUserPoint()
    }

    private fun setUserPoint(){
        GetUserinfoService(this).getUserInfo()
    }



    private fun setBtnListener(){

        // TODO 공개테스트, 프로덕션 버전에서 인앱결제 비활성화
//        binding.btn1000Charge.setOnClickListener { InappUtil.getPay(this,"point_1000") }
//        binding.btn3000Charge.setOnClickListener { InappUtil.getPay(this,"point_3000") }
//        binding.btn5000Charge.setOnClickListener { InappUtil.getPay(this,"point_5000") }
//        binding.btn8000Charge.setOnClickListener { InappUtil.getPay(this,"point_8000")  }
//        binding.btn10000Charge.setOnClickListener { InappUtil.getPay(this,"point_10000") }

        // TODO 강제충전 로직
        binding.btn1000Charge.setOnClickListener { makeModal(1000) }
        binding.btn3000Charge.setOnClickListener { makeModal(3000) }
        binding.btn5000Charge.setOnClickListener { makeModal(5000) }
        binding.btn8000Charge.setOnClickListener { makeModal(8000)  }
        binding.btn10000Charge.setOnClickListener { makeModal(10000) }

        binding.btnBackCharge.setOnClickListener { finish() }
    }

    private fun makeModal(point : Int){
        showTitleTwoButtonDialog(this,"        포인트 충전        ","$point 포인트를 충전하시겠습니까?","취소","확인",{
            dismissTitleTwoButtonDialog()
        },{
            forceCharging(ForceChargePointPostData(point))
        })
    }


    override fun onGetUserInfoSuccess(data: UserinfoData) {
        binding.tvPoint.text = data.point.toString()
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast(message)
    }

    override fun successBill() {
        setUserPoint()
    }

    override fun failBill() {
        showCustomToast("충전 실패")
    }

    private fun forceCharging(data : ForceChargePointPostData){
        val chargePointRetro = App.getRetro().create(ChargePointRetrofitInterface::class.java)
        chargePointRetro.postForceChargePoint(data)
            .enqueue(object : Callback<ChargePointResponse> {
                override fun onResponse(
                    call: Call<ChargePointResponse>,
                    response: Response<ChargePointResponse>
                ) {
                    dismissTitleTwoButtonDialog()
                    response.body()?.let{
                        if(response.code() == 200) {
                            setUserPoint()
                        }
                        else showCustomToast("API 통신 오류")
                    }
                    if(response.body() == null)  showCustomToast("API 통신 오류")
                }

                override fun onFailure(call: Call<ChargePointResponse>, t: Throwable) {
                    showCustomToast("네트워크 연결 오류")
                }
            })

    }

}
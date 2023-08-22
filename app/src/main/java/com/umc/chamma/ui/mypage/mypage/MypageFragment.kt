package com.umc.chamma.ui.mypage.mypage

import com.umc.chamma.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.umc.chamma.config.App
import com.umc.chamma.config.App.Companion.sharedPreferences
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentMypageBinding
import com.umc.chamma.ui.login.LoginActivity
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.mypage.chargepoint.ChargePointActivity
import com.umc.chamma.ui.mypage.chargepoint.GetUserinfoInterface
import com.umc.chamma.ui.mypage.chargepoint.GetUserinfoService
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData
import com.umc.chamma.util.Constants.TAG
import com.umc.chamma.util.Constants.X_LOGIN_TYPE

class MypageFragment : BaseFragmentVB<FragmentMypageBinding>(FragmentMypageBinding::bind, R.layout.fragment_mypage),GetUserinfoInterface{

    private val social by lazy{ sharedPreferences.getString(X_LOGIN_TYPE,"") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnListener()
    }

    private fun setBtnListener(){
        binding.btnUsageMypage.setOnClickListener{findNavController().navigate(R.id.action_mypageFragment_to_usageFragment)}
        binding.btnChargeMypage.setOnClickListener { startActivity(Intent(requireContext(),ChargePointActivity::class.java)) }
        binding.btnUpdateUserData.setOnClickListener { findNavController().navigate(R.id.action_mypageFragment_to_userinfoFragment) }
        binding.btnReview.setOnClickListener { findNavController().navigate((R.id.action_mypageFragment_to_reviewFragment)) }
        binding.btnLogoutMypage.setOnClickListener { logOut() }
    }

    override fun onResume() {
        super.onResume()
        GetUserinfoService(this).getUserInfo()
    }


    //로그아웃 코드
    fun logOut(){
        showOnlyTitleTwoButtonDialog(requireContext(),"로그아웃 하시나요?","취소","확인",
            {dismissOnlyTitleTwoButtonDialog()},{
                if (social == "KAKAO") kakaoLogout()
                else if (social == "NAVER") naverLogout()
            })
    }



    // 카카오 로그아웃
    private fun kakaoLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            else {
                sharedPreferences.edit().clear().apply()
                val intent = Intent(App.context(), LoginActivity::class.java)
                intent.apply{
                    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    (activity as MainActivity).finishAffinity()
                    startActivity(intent)
                }
            }
        }
    }

    // 네이버 로그아웃
    private fun naverLogout(){
        NaverIdLoginSDK.logout()
        sharedPreferences.edit().clear().apply()
        val intent = Intent(App.context(), LoginActivity::class.java)
        intent.apply{
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            (activity as MainActivity).finishAffinity()
            startActivity(intent)
        }
    }

    override fun onGetUserInfoSuccess(data: UserinfoData) {
        binding.tvNameMyypage.visibility = View.VISIBLE
        binding.tvPointMypage.visibility = View.VISIBLE
        binding.tvNimMypage.visibility = View.VISIBLE
        binding.tvPointMypage.text = data.point.toString()
        binding.tvNameMyypage.text = data.nickname

        if(!data.userProfile.isNullOrBlank()) binding.ivProfile.setImageURI(data.userProfile.toUri())
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast(message)
    }

}




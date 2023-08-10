package com.umc.chamma.ui.mypage.mypage

import com.umc.chamma.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.umc.chamma.config.App
import com.umc.chamma.config.App.Companion.sharedPreferences
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentMypageBinding
import com.umc.chamma.ui.login.LoginActivity
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.util.Constants.TAG
import com.umc.chamma.util.Constants.X_LOGIN_TYPE

class MypageFragment : BaseFragmentVB<FragmentMypageBinding>(FragmentMypageBinding::bind, R.layout.fragment_mypage){

    var mainActivity: MainActivity? = null
    private val social by lazy{ sharedPreferences.getString(X_LOGIN_TYPE,"") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMypageBinding.inflate(inflater, container, false)
        binding.btnUsageMypage.setOnClickListener{mainActivity?.mypageToUsage()}
        binding.btnChargeMypage.setOnClickListener { mainActivity?.mypageToCharge() }
        binding.btnUpdateUserData.setOnClickListener { mainActivity?.mypageToUpdate() }
        binding.btnReview.setOnClickListener { mainActivity?.mypageToReview() }
        binding.btnLogoutMypage.setOnClickListener { logOut() }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
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
                App.sharedPreferences.edit().clear().apply()
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
        App.sharedPreferences.edit().clear().apply()
        val intent = Intent(App.context(), LoginActivity::class.java)
        intent.apply{
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            (activity as MainActivity).finishAffinity()
            startActivity(intent)
        }
    }
}




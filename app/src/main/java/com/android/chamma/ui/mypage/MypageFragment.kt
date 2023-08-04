package com.android.chamma.ui.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.chamma.R
import com.android.chamma.config.App
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentMypageBinding
import com.android.chamma.ui.login.LoginActivity
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.util.Constants.TAG
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK

class MypageFragment : BaseFragmentVB<FragmentMypageBinding>(FragmentMypageBinding::bind, R.layout.fragment_mypage){

    var mainActivity: MainActivity? = null

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
        AlertDialog.Builder(mainActivity)
            .setTitle("알림")
            .setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("넵", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    //로그아웃 코드
                    naverLogout()
                    kakaoLogout()
                }
            })
            .setNegativeButton("아니요",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Log.d("logOut", "suspend logOut")
                }
            })
            .create()
            .show()
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
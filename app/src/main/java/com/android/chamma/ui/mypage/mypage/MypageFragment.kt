package com.android.chamma.ui.mypage.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.chamma.R
import com.android.chamma.config.BaseFragmentVB
import com.android.chamma.databinding.FragmentMypageBinding
import com.android.chamma.models.signupmodel.NickcheckResponse
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.ui.signup.network.NickcheckAPI
import com.android.chamma.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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



}
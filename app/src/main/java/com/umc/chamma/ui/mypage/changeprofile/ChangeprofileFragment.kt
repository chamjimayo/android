package com.umc.chamma.ui.mypage.changeprofile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentChangeProfileBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.mypage.chargepoint.GetUserinfoInterface
import com.umc.chamma.ui.mypage.chargepoint.GetUserinfoService
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoData
import com.umc.chamma.ui.signup.model.NickcheckResponse
import com.umc.chamma.ui.signup.network.NickcheckAPI


class ChangeprofileFragment : BaseFragmentVB<FragmentChangeProfileBinding>(FragmentChangeProfileBinding::bind, R.layout.fragment_change_profile),
GetUserinfoInterface {


    private var oldNick = ""
    private var newNick = ""
    private var isAvailableNick = false
    private var isProfileChange = false

    private lateinit var galleryLauncher : ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode == Activity.RESULT_OK){
                val uri = result.data?.data
                binding.btnProfileUser.setImageURI(uri)
                isProfileChange = true
                changeBtnAvailable()
            }
        }

        GetUserinfoService(this).getUserInfo()

        etFocusListener()
        btnListener()
        textChangeListener()

    }

    private fun openGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }


    private fun etFocusListener(){

        binding.etNick.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.etNick.setBackgroundResource(R.drawable.shape_user_et_focus)
                binding.btnDuplicheck.visibility = View.VISIBLE
            }else{
                binding.etNick.setBackgroundResource(R.drawable.shape_signup_et)
                binding.btnDuplicheck.visibility = View.INVISIBLE
            }
        }
    }

    private fun btnListener(){
        binding.btnDuplicheck.setOnClickListener {
            nickCheck()
        }

        binding.btnBackUpdate.setOnClickListener { findNavController().navigateUp() }
        binding.btnProfileUser.setOnClickListener{ openGallery() }
    }

    private fun textChangeListener(){

        binding.etNick.addTextChangedListener(object :  TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newNick = binding.etNick.text.toString()
                changeBtnAvailable()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun changeBtnAvailable(){
        if(((oldNick != newNick) && isAvailableNick) || isProfileChange){
            binding.btnSend2.isEnabled = true
            binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_duplicheck)
            binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        }else{
            binding.btnSend2.isEnabled = false
            binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_gender)
            binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext, R.color.chamma_signup_textgray))
        }
    }

    private fun nickCheck(){
        App.getRetro().create(NickcheckAPI::class.java)
            .checkNick(newNick).enqueue(object : Callback<NickcheckResponse>{
                override fun onResponse(
                    call: Call<NickcheckResponse>,
                    response: Response<NickcheckResponse>
                ) {
                    if(response.code() == 200){
                        if(response.body()?.data?.nicknameDuplicate == true){
                            (activity as MainActivity).runOnUiThread {
                                binding.tvAnnounce.text = "이미 사용중인 닉네임입니다."
                                binding.tvAnnounce.visibility = View.VISIBLE
                                isAvailableNick = false
                            }

                        }else{
                            (activity as MainActivity).runOnUiThread {
                                binding.tvAnnounce.text = "사용 가능한 닉네임입니다."
                                binding.tvAnnounce.visibility = View.VISIBLE
                                isAvailableNick = true
                                changeBtnAvailable()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<NickcheckResponse>, t: Throwable) {
                }
            })
    }

    override fun onGetUserInfoSuccess(data: UserinfoData) {
        binding.etName.setText(data.name)
        binding.etNick.setText(data.nickname)
        oldNick = data.nickname
        //TODO gender 적용,  사진 적용
        binding.btnMale.setBackgroundResource(R.drawable.shape_user_et_focus)
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast(message)
    }



}
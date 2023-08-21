package com.umc.chamma.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivitySignupBinding
import com.umc.chamma.ui.login.model.LoginResponseData
import com.umc.chamma.ui.signup.model.NickcheckResponse
import com.umc.chamma.ui.signup.model.SignupPostData
import com.umc.chamma.ui.signup.model.SignupResponse
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.signup.model.SignupResponseData
import com.umc.chamma.ui.signup.network.NickcheckAPI
import com.umc.chamma.ui.signup.network.SignupAPI
import com.umc.chamma.util.Constants
import com.umc.chamma.util.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : BaseActivityVB<ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    private var gender = ""
    private var nick = ""
    private var nickState = false
    private var name = ""
    private var authId = ""
    private var authType = ""

    private lateinit var galleryLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent.hasExtra("authId")){
            authId = intent.getStringExtra("authId").toString()
            authType = intent.getStringExtra("authType").toString()
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode == Activity.RESULT_OK){
                val uri = result.data?.data
                binding.btnProfile.setImageURI(uri)
            }
        }

        etFocusListener()
        btnListener()
        textChangeListener()
    }

    private fun etFocusListener(){
        binding.etName.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.etName.setBackgroundResource(R.drawable.shape_signup_et_focus)
            }else{
                binding.etName.setBackgroundResource(R.drawable.shape_signup_et)
            }
        }

        binding.etNick.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.etNick.setBackgroundResource(R.drawable.shape_signup_et_focus)
                binding.btnDuplicheck.visibility = View.VISIBLE
            }else{
                binding.etNick.setBackgroundResource(R.drawable.shape_signup_et)
                binding.btnDuplicheck.visibility = View.INVISIBLE
            }
        }
    }

    private fun btnListener(){

        binding.btnMale.setOnClickListener {
            gender = "male"
            binding.btnMale.setBackgroundResource(R.drawable.shape_signup_et_focus)
            binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_gender)
            if(name.isNotBlank() && nick.isNotBlank() && gender.isNotBlank()){
                binding.btnSend.isEnabled = true
                binding.btnSend.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
            }else{
                binding.btnSend.isEnabled = false
                binding.btnSend.setBackgroundResource(R.drawable.shape_signup_gender)
                binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
            }
        }

        binding.btnFemale.setOnClickListener {
            gender = "female"
            binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_et_focus)
            binding.btnMale.setBackgroundResource(R.drawable.shape_signup_gender)
            if(name.isNotBlank() && nick.isNotBlank() && gender.isNotBlank() && nickState){
                binding.btnSend.isEnabled = true
                binding.btnSend.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
            }else{
                binding.btnSend.isEnabled = false
                binding.btnSend.setBackgroundResource(R.drawable.shape_signup_gender)
                binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
            }
        }

        binding.btnDuplicheck.setOnClickListener {
            nickCheck()
        }

        binding.btnSend.setOnClickListener {
            sendSignup()
        }

        binding.btnProfile.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun textChangeListener(){
        binding.etName.addTextChangedListener(object :  TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = binding.etName.text.toString()
                if(name.isNotBlank() && nick.isNotBlank() && gender.isNotBlank() && nickState){
                    binding.btnSend.isEnabled = true
                    binding.btnSend.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                    binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                }else{
                    binding.btnSend.isEnabled = false
                    binding.btnSend.setBackgroundResource(R.drawable.shape_signup_gender)
                    binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etNick.addTextChangedListener(object :  TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nick = binding.etNick.text.toString()
                nickState = false
                if(name.isNotBlank() && nick.isNotBlank() && gender.isNotBlank() && nickState){
                    binding.btnSend.isEnabled = true
                    binding.btnSend.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                    binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                }else{
                    binding.btnSend.isEnabled = false
                    binding.btnSend.setBackgroundResource(R.drawable.shape_signup_gender)
                    binding.btnSend.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun nickCheck(){
        com.umc.chamma.config.App.getRetro().create(NickcheckAPI::class.java)
            .checkNick(nick).enqueue(object : Callback<NickcheckResponse>{
                override fun onResponse(
                    call: Call<NickcheckResponse>,
                    response: Response<NickcheckResponse>
                ) {
                    if(response.code() == 200){
                        if(response.body()?.data?.nicknameDuplicate == true){
                            runOnUiThread {
                                binding.tvAnnounce.text = "이미 사용중인 닉네임입니다."
                                binding.tvAnnounce.visibility = View.VISIBLE
                                nickState = false
                            }

                        }else{
                            runOnUiThread {
                                binding.tvAnnounce.text = "사용 가능한 닉네임입니다."
                                binding.tvAnnounce.visibility = View.VISIBLE
                                nickState = true
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<NickcheckResponse>, t: Throwable) {
                }
            })
    }

    private fun sendSignup(){
        val data = SignupPostData(authType,authId,name,nick,gender)
        Log.d(TAG,"$data")
        com.umc.chamma.config.App.getRetro().create(SignupAPI::class.java)
            .signup(data).enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {

                    if(response.code() == 200) {
                        storeTokens(response.body()!!.data)
                        // 회원가입 성공
                        val intent = Intent(this@SignupActivity, MainActivity::class.java)
                        startActivity(intent)
                    }else{

                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.d(TAG,"${t.message}")
                }
            })
    }

    private fun storeTokens(result : SignupResponseData){
        App.sharedPreferences.edit()
            .putString(Constants.X_ACCESS_TOKEN, "Bearer " + result.accessToken)
            .putString(Constants.X_REFRESH_TOKEN, result.refreshToken)
            .putString(Constants.X_ACCESS_EXPIRE, result.accessTokenExpiredDate)
            .putString(Constants.X_REFRESH_TOKEN, result.refreshTokenExpiredDate)
            .apply()
    }

}
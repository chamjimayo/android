package com.android.chamma.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.android.chamma.R
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivitySignupBinding
import com.android.chamma.models.loginmodel.LoginResponse
import com.android.chamma.models.signupmodel.NickcheckResponse
import com.android.chamma.models.signupmodel.SignupPostData
import com.android.chamma.models.signupmodel.SignupResponse
import com.android.chamma.ui.login.network.LoginAPI
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.ui.signup.network.NickcheckAPI
import com.android.chamma.ui.signup.network.SignupAPI
import com.android.chamma.util.Constants
import com.android.chamma.util.Constants.TAG
import com.android.chamma.util.RetrofitInterface
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent.hasExtra("authId")){
            authId = intent.getStringExtra("authId").toString()
            authType = intent.getStringExtra("authType").toString()
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
            binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_gender)
            binding.btnMale.setBackgroundResource(R.drawable.shape_signup_et_focus)
        }

        binding.btnFemale.setOnClickListener {
            binding.btnMale.setBackgroundResource(R.drawable.shape_signup_gender)
            binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_et_focus)
        }

        binding.btnDuplicheck.setOnClickListener {

        }

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

        binding.btnDuplicheck.setOnClickListener {
            nickCheck()
        }

        binding.btnSend.setOnClickListener {
            sendSignup()
        }
    }

    private fun textChangeListener(){
        binding.etName.addTextChangedListener(object :  TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = binding.etName.text.toString()
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
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etNick.addTextChangedListener(object :  TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nick = binding.etNick.text.toString()
                nickState = false
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
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun nickCheck(){
        RetrofitInterface.retrofit.create(NickcheckAPI::class.java)
            .checkNick(nick).enqueue(object : Callback<NickcheckResponse>{
                override fun onResponse(
                    call: Call<NickcheckResponse>,
                    response: Response<NickcheckResponse>
                ) {
                    if(response.code() == 0){
                        if(response.body()?.data?.nicknameDuplicate == true){
                            binding.tvAnnounce.visibility = View.VISIBLE
                            nickState = true
                        }else{

                        }
                    }else{

                    }
                }

                override fun onFailure(call: Call<NickcheckResponse>, t: Throwable) {
                }
            })
    }

    private fun sendSignup(){
        val data = SignupPostData(authType,authId,name,nick,gender)
        Log.d(TAG,"$data")
        RetrofitInterface.retrofit.create(SignupAPI::class.java)
            .signup(data).enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if(response.code() == 0){
                        // 회원가입 성공
                        val intent = Intent(this@SignupActivity,MainActivity::class.java)
                        startActivity(intent)

                    }else{
                        // 회원가입 실패
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.d(TAG,"${t.message}")
                }
            })
    }

}
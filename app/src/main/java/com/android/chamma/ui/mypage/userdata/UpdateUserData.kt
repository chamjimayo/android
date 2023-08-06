package com.android.chamma.ui.mypage.userdata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.android.chamma.R
import com.android.chamma.databinding.FragmentUpdateUserDataBinding
import com.android.chamma.models.signupmodel.NickcheckResponse
import com.android.chamma.models.signupmodel.SignupPostData
import com.android.chamma.models.signupmodel.SignupResponse
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.ui.signup.network.NickcheckAPI
import com.android.chamma.ui.signup.network.SignupAPI
import com.android.chamma.util.RetrofitInterface
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.android.chamma.util.Constants.TAG



class UpdateUserData : Fragment() {

    lateinit var mainActivity: MainActivity

    private var gender = ""
    private var nick = ""
    private var nickState = false
    private var name = ""
    private var authId = ""
    private var authType = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentUpdateUserDataBinding.inflate(inflater, container, false)

        fun nickCheck(){
            RetrofitInterface.retrofit.create(NickcheckAPI::class.java)
                .checkNick(nick).enqueue(object : Callback<NickcheckResponse>{
                    override fun onResponse(
                        call: Call<NickcheckResponse>,
                        response: Response<NickcheckResponse>
                    ) {
                        if(response.code() == 200){
                            if(response.body()?.data?.nicknameDuplicate == true){
                                mainActivity.runOnUiThread {
                                    binding.tvAnnounce.text = "이미 사용중인 닉네임입니다."
                                    binding.tvAnnounce.visibility = View.VISIBLE
                                    nickState = false
                                }

                            }else{
                                mainActivity.runOnUiThread {
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

//        fun sendSignup(){
//            val data = SignupPostData(authType,authId,name,nick,gender)
//            Log.d(TAG,"$data")
//            RetrofitInterface.retrofit.create(SignupAPI::class.java)
//                .signup(data).enqueue(object : Callback<SignupResponse> {
//                    override fun onResponse(
//                        call: Call<SignupResponse>,
//                        response: Response<SignupResponse>
//                    ) {
//                        if(response.code() == 200) {
//                            // 회원가입 성공
//                            val intent = Intent(this@SignupActivity, MainActivity::class.java)
//                            startActivity(intent)
//                        }else{
//
//                        }
//                    }
//
//                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
//                        Log.d(TAG,"${t.message}")
//                    }
//                })
//        }

        fun etFocusListener(){
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

        fun btnListener(){
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
                    binding.btnSend2.isEnabled = true
                    binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                    binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                }else{
                    binding.btnSend2.isEnabled = false
                    binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_gender)
                    binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
                }
            }

            binding.btnFemale.setOnClickListener {
                gender = "female"
                binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_et_focus)
                binding.btnMale.setBackgroundResource(R.drawable.shape_signup_gender)
                if(name.isNotBlank() && nick.isNotBlank() && gender.isNotBlank() && nickState){
                    binding.btnSend2.isEnabled = true
                    binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                    binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                }else{
                    binding.btnSend2.isEnabled = false
                    binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_gender)
                    binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
                }
            }

            binding.btnDuplicheck.setOnClickListener {
                nickCheck()
            }

            binding.btnSend2.setOnClickListener {
//                sendSignup()
            }
        }

        fun textChangeListener(){
            binding.etName.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    name = binding.etName.text.toString()
                    if(name.isNotBlank() && nick.isNotBlank() && gender.isNotBlank() && nickState){
                        binding.btnSend2.isEnabled = true
                        binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                        binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                    }else{
                        binding.btnSend2.isEnabled = false
                        binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_gender)
                        binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
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
                        binding.btnSend2.isEnabled = true
                        binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_duplicheck)
                        binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                    }else{
                        binding.btnSend2.isEnabled = false
                        binding.btnSend2.setBackgroundResource(R.drawable.shape_signup_gender)
                        binding.btnSend2.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.chamma_signup_textgray))
                    }
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })
        }




        binding.btnBackUpdate.setOnClickListener { mainActivity.goBackMypage() }
        etFocusListener()
        btnListener()
        textChangeListener()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }



}
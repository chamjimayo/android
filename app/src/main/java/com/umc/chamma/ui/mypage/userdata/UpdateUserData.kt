package com.umc.chamma.ui.mypage.userdata

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.umc.chamma.R
import com.umc.chamma.config.App
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.config.BaseFragmentVB
import com.umc.chamma.databinding.FragmentHomeBinding
import com.umc.chamma.databinding.FragmentUpdateUserDataBinding
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.signup.model.NickcheckResponse
import com.umc.chamma.ui.signup.model.SignupPostData
import com.umc.chamma.ui.signup.model.SignupResponse
import com.umc.chamma.ui.signup.model.SignupResponseData
import com.umc.chamma.ui.signup.network.NickcheckAPI
import com.umc.chamma.ui.signup.network.SignupAPI
import com.umc.chamma.util.Constants


class UpdateUserData : com.umc.chamma.config.BaseFragmentVB<FragmentUpdateUserDataBinding>(FragmentUpdateUserDataBinding::bind, R.layout.fragment_update_user_data) {

    lateinit var mainActivity: MainActivity

    private var gender = ""
    private var nick = ""
    private var nickState = false
    private var name = ""
    private var authId = ""
    private var authType = ""

//    val PERM_STORAGE = 99
//    val PERM_CAMERA = 100
//    val REQ_CAMERA = 101
//    val REQ_STORAGE = 102
//
//    var realUri: Uri? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(mainActivity.intent.hasExtra("authId")){
            authId = mainActivity.intent.getStringExtra("authId").toString()
            authType = mainActivity.intent.getStringExtra("authType").toString()
        }

        etFocusListener()
        btnListener()
        textChangeListener()

        binding.btnBackUpdate.setOnClickListener { findNavController().navigateUp() }
        binding.btnProfileUser.setOnClickListener { navigatePhotos() }
    }

    private fun etFocusListener(){
        binding.etName.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.etName.setBackgroundResource(R.drawable.shape_user_et_focus)
            }else{
                binding.etName.setBackgroundResource(R.drawable.shape_user_et_focus)
            }
        }

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
        binding.btnMale.setOnClickListener {
            binding.btnFemale.setBackgroundResource(R.drawable.shape_signup_gender)
            binding.btnMale.setBackgroundResource(R.drawable.shape_user_et_focus)
        }

        binding.btnFemale.setOnClickListener {
            binding.btnMale.setBackgroundResource(R.drawable.shape_signup_gender)
            binding.btnFemale.setBackgroundResource(R.drawable.shape_user_et_focus)
        }

        binding.btnDuplicheck.setOnClickListener {

        }

        binding.btnMale.setOnClickListener {
            gender = "male"
            binding.btnMale.setBackgroundResource(R.drawable.shape_user_et_focus)
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
            binding.btnFemale.setBackgroundResource(R.drawable.shape_user_et_focus)
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
            sendSignup()
        }
    }

    private fun textChangeListener(){
        binding.etName.addTextChangedListener(object :  TextWatcher{
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

    private fun nickCheck(){
        com.umc.chamma.config.App.getRetro().create(NickcheckAPI::class.java)
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

    private fun sendSignup(){
        val data = SignupPostData(authType,authId,name,nick,gender)
        Log.d(Constants.TAG,"$data")
        com.umc.chamma.config.App.getRetro().create(SignupAPI::class.java)
            .signup(data).enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {

                    if(response.code() == 200) {
                        storeTokens(response.body()!!.data)
                        // 회원가입 성공
                        val intent = Intent(mainActivity, MainActivity::class.java)
                        startActivity(intent)
                    }else{

                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.d(Constants.TAG,"${t.message}")
                }
            })
    }

    private fun storeTokens(result : SignupResponseData){
        App.sharedPreferences.edit()
            .putString(Constants.X_ACCESS_TOKEN, "Bearer " + result.accessToken)
            .putString(Constants.X_REFRESH_TOKEN, result.refreshToken)
            .putString(Constants.X_ACCESS_EXPIRE, result.accessTokenValidityMs.toString())
            .putString(Constants.X_REFRESH_TOKEN, result.refreshTokenValidityMs.toString())
            .apply()
    }

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,2000)
    }

    fun changeProfile() {
        when {
            ContextCompat.checkSelfPermission(mainActivity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                // READ_EXTERNAL_STORAGE의 권한이 PERMISSION_GRANTED와 같다면..
                //TODO 권한이 잘 부여되었을 때상황, 갤러리에서 사진을 선택하는 코드 구현
                navigatePhotos()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // 권한을 명시적으로 거부한 경우 true
                // 처음보거나, 다시묻지 않음을 선택한 경우 false
                //TODO 교육용 팝업 확인 후 권한 팝업을 띄우는 기능
                showPermissionContextPopup()
            }
            else -> {
                // 처음봤을때 띄워줌
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(mainActivity)
            .setTitle("권한이 필요합니다")
            .setMessage("전자액자에서 사진을 선택하려면 권한이 필요합니다.")
            .setPositiveButton("동의하기", {_, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
            })
            .setNegativeButton("취소하기",{ _,_ ->})
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // 권한이 부여 된 것입니다.
                    // 허용 클릭 시
                    navigatePhotos()
                } else {
                    // 거부 클릭시
                    Toast.makeText(mainActivity,"권한을 거부했습니다.",Toast.LENGTH_SHORT).show()
                }
            } else -> {
            //Do Nothing
        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(mainActivity, "잘못된 접근입니다", Toast.LENGTH_SHORT).show()
            return
        }

        when (requestCode) {
            2000 -> {
                val selectedImageURI: Uri? = data?.data


//                if(imageUriList.size == 6 ) {
//                    Toast.makeText(mainActivity,"이미지가 가득 찼습니다.",Toast.LENGTH_SHORT).show()
//                    return
//                }
                if( selectedImageURI != null) {
                    binding.btnProfileUser.setImageURI(selectedImageURI)
                    
                }else {
                    Toast.makeText(mainActivity,"사진을 가져오지 못했습니다",Toast.LENGTH_SHORT).show()
                }
//            } else -> {
//            Toast.makeText(mainActivity,"잘못된 접근입니다",Toast.LENGTH_SHORT).show()
//        }
            }
        }
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) mainActivity = context
    }

}
package com.umc.chamma.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.umc.chamma.config.App.Companion.sharedPreferences
import com.umc.chamma.databinding.ActivityLoginBinding
import com.umc.chamma.ui.login.model.LoginPostData
import com.umc.chamma.ui.login.model.LoginResponseData
import com.umc.chamma.ui.main.MainActivity
import com.umc.chamma.ui.signup.SignupActivity
import com.umc.chamma.util.Constants.TAG
import com.umc.chamma.util.Constants.X_ACCESS_EXPIRE
import com.umc.chamma.util.Constants.X_ACCESS_TOKEN
import com.umc.chamma.util.Constants.X_REFRESH_TOKEN
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.umc.chamma.util.Constants.RC_PERMISSION
import com.umc.chamma.util.Constants.X_LOGIN_TYPE
import com.umc.chamma.util.LoginType.social

class LoginActivity : com.umc.chamma.config.BaseActivityVB<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityInterface {

    private lateinit var neededPermissionList : ArrayList<String>
    private val requiredPermissionList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        arrayOf(  // 안드로이드 13 이상 필요한 권한들
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }else{
        arrayOf(  // 안드로이드 13 미만 필요한 권한들
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        onCheckPermissions()

        binding.btnKakaoLogin.setOnClickListener {
            social = "KAKAO"
            showLoading()
            kakaoLogin()
        }

        binding.btnNaverLogin.setOnClickListener {
            social = "NAVER"
            showLoading()
            naverLogin()
        }
    }

    private fun onCheckPermissions(){
        neededPermissionList = arrayListOf<String>()

        requiredPermissionList.forEach{permission->
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED) neededPermissionList.add(permission)
        }

        if(neededPermissionList.isNotEmpty()){
            ActivityCompat.requestPermissions(this, neededPermissionList.toArray(arrayOf<String>()), RC_PERMISSION)
        }
    }


    private fun naverLogin() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 로그인 성공시, 정보 불러오기
                naverCallInfo()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                dismissLoading()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
                dismissLoading()
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    private fun kakaoLogin() {
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "앱 로그인 실패 $error")
                    dismissLoading()
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            this,
                            callback = kakaoEmailCb
                        ) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.d(TAG, "앱 로그인 성공 ${token.accessToken}")
                    // 로그인 성공시 정보 불러오기
                    kakaoCallInfo()
                }
            }
        } else {
            dismissLoading()
            UserApiClient.instance.loginWithKakaoAccount(
                this,
                callback = kakaoEmailCb
            ) // 카카오 이메일 로그인
        }
    }


    // 카카오톡 이메일 로그인 콜백
    private val kakaoEmailCb: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "이메일 로그인 실패 $error")
            dismissLoading()
        } else if (token != null) {
            Log.d(TAG, "이메일 로그인 성공 ${token.accessToken}")
            // 로그인 성공시 정보 불러오기
            kakaoCallInfo()
        }
    }

    // 네이버 유저정보 가져오기
    private fun naverCallInfo(){
        NidOAuthLogin().callProfileApi(profileCallback)
    }


    // 네이버 유저정보 콜백
    private val profileCallback = object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(result: NidProfileResponse) {
            val id = result.profile?.id

            // 식별아이디로 통신
            LoginService(this@LoginActivity).postLogin(LoginPostData(id.toString()))
        }
        override fun onFailure(httpStatus: Int, message: String) {
            dismissLoading()
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
        }
        override fun onError(errorCode: Int, message: String) {
            dismissLoading()
            onFailure(errorCode, message)
        }
    }

    // 카카오 유저정보 불러오기
    private fun kakaoCallInfo(){
        // 로그인 유저정보 불러오기
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패 $error")
                dismissLoading()
            } else if (user != null) {
                Log.d(TAG, "사용자 정보 요청 성공 : $user")
                // 식별아이디로 통신
                LoginService(this@LoginActivity).postLogin(LoginPostData(user.id.toString()))
            }
        }
    }
    private fun storeTokens(result : LoginResponseData){
        sharedPreferences.edit()
            .putString(X_ACCESS_TOKEN, "Bearer " + result.accessToken)
            .putString(X_REFRESH_TOKEN, result.refreshToken)
            .putString(X_ACCESS_EXPIRE, result.accessTokenValidityMs.toString())
            .putString(X_REFRESH_TOKEN, result.refreshTokenValidityMs.toString())
            .putString(X_LOGIN_TYPE, social)
            .apply()
    }

    override fun onPostLoginSuccess(result : LoginResponseData) {
        // 존재하는 유저. 로그인
        dismissLoading()

        // accessToken/refreshToken 저장
        storeTokens(result)

        // MainActivity로 이동
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onPostLoginFailure(message : String, uuid : String) {
        Log.d(TAG,"$uuid")
        dismissLoading()
        if(uuid.isNotBlank()){
            // 존재하지 않는 유저. 회원가입
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                .putExtra("authType",social)
                .putExtra("authId",uuid)
            startActivity(intent)
        }
    }

    // 풀스크린 적용
    private fun setFullScreen(){
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

}
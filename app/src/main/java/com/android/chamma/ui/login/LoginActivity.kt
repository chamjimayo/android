package com.android.chamma.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivityLoginBinding
import com.android.chamma.models.loginmodel.LoginPostData
import com.android.chamma.models.loginmodel.LoginResponse
import com.android.chamma.ui.login.network.LoginAPI
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.ui.signup.SignupActivity
import com.android.chamma.util.Constants.TAG
import com.android.chamma.util.Jwt
import com.android.chamma.util.RetrofitInterface
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivityVB<ActivityLoginBinding>(ActivityLoginBinding::inflate) {


    private var social = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        binding.btnKakaoLogin.setOnClickListener {
            social = "KAKAO"
            kakaoLogin()
        }

        binding.btnNaverLogin.setOnClickListener {
            social = "NAVER"
            naverLogin()
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
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
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
        override fun onSuccess(response: NidProfileResponse) {
            val id = response.profile?.id
            // 식별아이디로 통신
            senduuid(id.toString())
        }
        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    // 카카오 유저정보 불러오기
    private fun kakaoCallInfo(){
        // 로그인 유저정보 불러오기
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.d(TAG, "사용자 정보 요청 성공 : $user")
                // 식별아이디로 통신
                senduuid(user.id.toString())
            }
        }
    }

     private fun senduuid(uuid : String){
         val data = LoginPostData(uuid)
         RetrofitInterface.retrofit.create(LoginAPI::class.java)
             .checkUuid(data).enqueue(object : Callback<LoginResponse>{
                 override fun onResponse(
                     call: Call<LoginResponse>,
                     response: Response<LoginResponse>
                 ) {
                     Log.d(TAG,"${response.body()?.data}")
                     if(response.code() == 200){
                         // 존재하는 유저. 로그인
                         // accessToken 저장
                         Jwt.setjwt(response.body()?.data!!.accessToken)
                         
                         // MainActivity로 이동
                         val intent = Intent(this@LoginActivity, MainActivity::class.java)
                         startActivity(intent)
                     }else {
                         // 존재하지 않는 유저. 회원가입
                         val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                             .putExtra("authType",social)
                             .putExtra("authId",uuid)
                         startActivity(intent)
                     }

                 }
                 override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                     Log.d(TAG,"${t.message}")
                 }
             })
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
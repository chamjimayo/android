package com.android.chamma.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.chamma.config.BaseActivityVB
import com.android.chamma.databinding.ActivityLoginBinding
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.util.Constants.TAG
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class LoginActivity : BaseActivityVB<ActivityLoginBinding>(ActivityLoginBinding::inflate) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnKakaoLogin.setOnClickListener {
            kakaoLogin()
        }

        binding.btnNaverLogin.setOnClickListener {
            naverLogin()
        }
    }


    private fun naverLogin() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
//                naverCallInfo()
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
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
//                    kakaoCallInfo()
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
            val intent = Intent(this, MainActivity::class.java)
                .putExtra("kakao", "kakao")
            startActivity(intent)
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
            val name = response.profile?.name
            val nick = response.profile?.nickname
            val age = response.profile?.age
            val email = response.profile?.email
            val birthYear = response.profile?.birthYear
            Log.d(TAG,"$id $name $nick")
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
                val id = user.id
                val nickname = user.kakaoAccount?.profile?.nickname
                val birthday = user.kakaoAccount?.birthday
                val email = user.kakaoAccount?.email
                val age = user.kakaoAccount?.ageRange.toString()
                Log.d(TAG,id.toString() + "\n" + nickname + "\n" +birthday + "\n" + email + "\n" + age)
            }
        }
    }

}
package com.android.chamma.ui.login

import android.content.Intent
import android.util.Log
import com.android.chamma.models.loginmodel.LoginPostData
import com.android.chamma.models.loginmodel.LoginResponse
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.ui.signup.SignupActivity
import com.android.chamma.util.Constants
import com.android.chamma.util.Jwt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view : LoginActivityInterface) {


    fun checkUuid(){

        val loginRetro = RetrofitInterface.

        val data = LoginPostData(uuid)
        RetrofitInterface.retrofit.create(LoginAPI::class.java)
            .checkUuid(data).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.d(Constants.TAG,"${response.body()?.data}")
                    response.body()?.let{
                        if(response.code() == 200){
                            // 존재하는 유저. 로그인
                            // accessToken 저장
                            Jwt.setjwt(response.body()?.data!!.accessToken)
                            Jwt.setRefreshToken(response.body()?.data!!.refreshToken)

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

                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(Constants.TAG,"${t.message}")
                }
            })
    }



}
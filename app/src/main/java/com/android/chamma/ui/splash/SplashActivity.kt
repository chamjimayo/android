package com.android.chamma.ui.splash

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.chamma.config.App.Companion.sharedPreferences
import com.android.chamma.R
import com.android.chamma.ui.login.LoginActivity
import com.android.chamma.ui.main.MainActivity
import com.android.chamma.util.Constants.TAG
import com.android.chamma.util.Constants.X_ACCESS_TOKEN
import com.android.chamma.util.LoadingDialog

class SplashActivity : AppCompatActivity() {

    private val dialog = LoadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        setFullScreen()
        Handler(Looper.getMainLooper()).postDelayed({

            // 스플래시 끝난뒤 LoadingDialog 띄우기
            dialog.show(supportFragmentManager,"dialog")

            if (!isNetworkConnected(this)) {
                dialog.dismiss()
                showAlert()
            } else {
                dialog.dismiss()
                autoLogin()
                finish()
            }
        }, 1500)
    }

    private fun autoLogin(){
        val jwt = sharedPreferences.getString(X_ACCESS_TOKEN,"")


        /* TODO
            ACCESS_TOKEN 유효기간 확인 
            -> 안지났을 경우 : MainActivity 로 이동
            -> 지났을 경우 : REFRESH_TOKEN 유효기간 확인
                -> 안지났을 경우 : /api/auth/token/access 로 ACCESSTOKEN 갱신
                -> 지났을 경우 : LoginActivity 로 이동
         */

        // ACCESS_TOKEN 유효기간 무한이라고 가정하고 우선 작성
        if (!jwt.isNullOrBlank()) startActivity(Intent(this, MainActivity::class.java))
        else startActivity(Intent(this, LoginActivity::class.java))

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
            .setMessage("네트워크에 연결되지 않았습니다.")
            .setPositiveButton("다시 시도하기",
                DialogInterface.OnClickListener { dialog, id ->
                    // 앱 처음부터 다시시작
                    val intent = Intent(applicationContext, SplashActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                })
        builder.show()
    }

    // 네트워크 연결되었는지 검사코드
    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
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
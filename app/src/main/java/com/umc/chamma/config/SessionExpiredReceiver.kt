package com.umc.chamma.config

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.umc.chamma.ui.login.LoginActivity
import com.umc.chamma.util.Constants.SESSION_EXPIRED_ACTION

class SessionExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SESSION_EXPIRED_ACTION) {
            // TODO 세션 만료 모달 추가
            val expiredIntent = Intent(context, LoginActivity::class.java)
            expiredIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(expiredIntent)
        }
    }
}
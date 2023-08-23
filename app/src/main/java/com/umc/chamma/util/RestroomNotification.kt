package com.umc.chamma.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.umc.chamma.R
import com.umc.chamma.ui.main.MainActivity
import okhttp3.internal.notify

class RestroomNotification(val context: Context?) {
    val orderId = 1L
    private val orderChannelId = "411"
    private lateinit var useCloseAction:NotificationCompat.Action

    fun createNotification() {
        //val orderTitle = intent?.getStringExtra(context!!.getString(R.string.order_title))!!

        val orderContent = "화장실을 이용 중이에요!"

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
        useCloseAction=NotificationCompat.Action.Builder(R.drawable.banner_btn_icon,"이용종료하기",pendingIntent).build()

        val builder =
            createNotificationBuilder(context, orderChannelId, orderContent, pendingIntent)
        //val orderName = context.getString(R.string.order_name)

        if (context == null) return
        createNotificationChannel(context, "orderName", orderChannelId, orderContent)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(orderChannelId.toInt(), builder.build())
        }
    }

    fun removeNotification() {
        val notificationManager: NotificationManager? =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager?.cancel(orderChannelId.toInt())
    }

    private fun createNotificationBuilder(
        context: Context?,
        orderChannelId: String,
        orderContent: String,
        pendingIntent: PendingIntent?
    ) =
        NotificationCompat.Builder(context!!, orderChannelId)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.mipmap.chamma_logo_round)
            .setContentText(orderContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(useCloseAction)
            .setAutoCancel(false)

    private fun createNotificationChannel(
        context: Context,
        orderName: String,
        orderChannelId: String,
        orderContent: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                orderChannelId,
                orderName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = orderContent
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}


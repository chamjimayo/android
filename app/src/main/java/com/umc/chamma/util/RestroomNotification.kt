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
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.umc.chamma.R
import com.umc.chamma.ui.main.MainActivity
import okhttp3.internal.notify
import android.app.Service
import android.os.IBinder
import androidx.core.app.ServiceCompat.stopForeground
import com.umc.chamma.config.App
import com.umc.chamma.ui.main.MainActivity.Companion.ACTION_STOP
import com.umc.chamma.ui.using.UsingActivity

//(val context: Context?)
class RestroomNotification : Service() {
    val orderId = 1L
    private val orderChannelId = "411"
    private lateinit var useCloseAction: NotificationCompat.Action

    private fun createCustomContentView(): RemoteViews {
        return RemoteViews(App.context()?.packageName, R.layout.item_notification)/*.apply {
            setOnClickPendingIntent(
                R.id.closeBtn,
                PendingIntent.getActivity(
                    context,
                    1000,
                    Intent(context, MainActivity::class.java),
                    FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                )
            )
        }
                    */

    }

    fun createNotification() {
        //val orderTitle = intent?.getStringExtra(context!!.getString(R.string.order_title))!!

        val orderContent = "화장실을 이용 중이에요!"

        val pendingIntent = PendingIntent.getActivity(
            App.context(),
            0,
            Intent(App.context(), MainActivity::class.java),
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            //0
        )

        val pendingIntent2 = PendingIntent.getActivity(
            App.context(),
            0,
            Intent(App.context(), UsingActivity::class.java),
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
        useCloseAction =
            NotificationCompat.Action.Builder(R.drawable.banner_btn_icon, "이용종료하기", pendingIntent2)
                .build()

        val builder =
            createNotificationBuilder(App.context(), orderChannelId, orderContent, pendingIntent)
        //val orderName = context.getString(R.string.order_name)

        if (App.context() == null) return
        createNotificationChannel(App.context(), "orderName", orderChannelId, orderContent)

        /*with(NotificationManagerCompat.from(App.context())) {
            if (ActivityCompat.checkSelfPermission(
                    App.context(),
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
            }
         */
        startForeground(orderChannelId.toInt(), builder.build())
    }


    fun removeNotification() {
        val notificationManager: NotificationManager? =
            App.context()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //notificationManager?.cancel(orderChannelId.toInt())
        notificationManager?.cancelAll()

    }

    private fun createNotificationBuilder(
        context: Context?,
        orderChannelId: String,
        orderContent: String,
        pendingIntent: PendingIntent?
    ) =
        NotificationCompat.Builder(context!!, orderChannelId)
            //.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.mipmap.chamma_logo_round)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomBigContentView(createCustomContentView())
            .setCustomContentView(createCustomContentView())

            .setContentText(orderContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            //.setContentIntent(pendingIntent)
            .addAction(useCloseAction)
            .setShowWhen(false)
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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null && intent.action!!.equals(
                ACTION_STOP, ignoreCase = true)) {
            stopForeground(true)
            stopSelf()
        }

        createNotification()

        return START_STICKY//super.onStartCommand(intent, flags, startId)
    }
}


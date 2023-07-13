package com.cy.testapp.activity

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.chenyue404.androidlib.extends.bind
import com.chenyue404.androidlib.extends.click
import com.chenyue404.androidlib.extends.log
import com.chenyue404.androidlib.widget.BaseActivity
import com.cy.testapp.R

/**
 * Created by cy on 2023/7/13.
 */
class NotificationActivity : BaseActivity() {
    private val bt0 by bind<Button>(R.id.bt_0)

    private val msgId = 1
    private val requestNotificationPermissionCode = 1000

    override fun getContentViewResId() = R.layout.activity_anim_pop
    override fun initView() {
        bt0.click {
            showNotification()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        log("onNewIntent")
    }

    private fun showNotification() {
        val channelId = "channelId"
        val builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, channelId)
            } else {
                Notification.Builder(this)
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
        val notification: Notification =
            builder
                .setOngoing(true)
                .setSmallIcon(R.drawable.main_avartar_male)
                .setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setStyle(Notification.BigTextStyle().bigText("bigText"))
                .setContentIntent(
                    PendingIntent.getActivity(
                        this,
                        0,
//                        ContextProvider.mContext.packageManager.getLaunchIntentForPackage(mContext.packageName)
                        Intent(mContext, NotificationActivity::class.java)
                            ?.apply {
//                                addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
//                                addFlags(
//                                    Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                            or Intent.FLAG_ACTIVITY_SINGLE_TOP
//                                )
//                                addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)
//                                addFlags(
//                                    Intent.FLAG_ACTIVITY_SINGLE_TOP
//                                            or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                )
//                                action = Intent.ACTION_MAIN
//                                addCategory(Intent.CATEGORY_LAUNCHER)
//                                putExtra("android.pendingIntent.backgroundActivityAllowed", true)
                            },
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
//                .addAction(
//                    Notification.Action.Builder(
//                        R.drawable.qr_code,
//                        getString(R.string.qrcode),
//                        PendingIntent.getActivity(
//                            this,
//                            0,
//                            Intent(this, QRCodeActivity::class.java),
//                            0
//                        )
//                    ).build()
//                )
//                .addAction(
//                    Notification.Action.Builder(
//                        android.R.drawable.ic_media_pause,
//                        getString(R.string.end_server),
//                        PendingIntent.getBroadcast(
//                            this,
//                            0,
//                            Intent(this, StopServerReceiver::class.java),
//                            0
//                        )
//                    ).build()
//                )
                .build()
        with(NotificationManagerCompat.from(this)) {
            if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@NotificationActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    requestNotificationPermissionCode
                )
                return
            }
            notify(msgId, notification)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestNotificationPermissionCode
            && grantResults.first() == PackageManager.PERMISSION_GRANTED
        ) {
            showNotification()
        }
    }
}
package com.app.quench.log.setting

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.annotation.DoNotInline

class QusnSeervice : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService(this)
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService(context: Service) {
        try {
            createChannel(context)
            context.startForeground(1, createNotify(context))
        } catch (_: Exception) {
        }
    }

    @DoNotInline
    private fun createChannel(context: Context) {
        val channel = NotificationChannel("addf", "notice", NotificationManager.IMPORTANCE_DEFAULT)
        val service = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
    }

    @DoNotInline
    private fun createNotify(context: Context): Notification {
        return Notification.Builder(context, "addf")
            .setContentTitle("")
            .setContentText("")
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .build()
    }

}
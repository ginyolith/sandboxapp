package com.example.sandboxapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ForegroundInfo
import androidx.work.WorkManager

class NotificationCreator(private val applicationContext : Context) {

    private val notificationManager
        get() = NotificationManagerCompat.from(applicationContext)

    fun createForegroundInfo(progress: String, onGoing: Boolean): ForegroundInfo {
        // Build a notification using bytesRead and contentLength
        val context = applicationContext
        val id = "default"
        val title = "コンタクトの保存"
        val cancel = "cancel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(id, "My Background Service")
        }

        val notification: Notification = NotificationCompat.Builder(context, id)
            .setContentTitle(title)
            .setTicker(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(progress)
            .setOngoing(onGoing) // Add the cancel action to the notification which can
            .build()

        notificationManager.notify(101, notification)

        return ForegroundInfo(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "Local Notification Sample"
        notificationManager.createNotificationChannel(channel)
        return channelId
    }
}
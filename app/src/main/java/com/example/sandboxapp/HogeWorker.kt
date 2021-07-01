package com.example.sandboxapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters


class HogeWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    val creator by lazy { NotificationCreator(appContext) }

    override fun doWork(): Result {
        creator.createForegroundInfo("保存中です", true)
        Thread.sleep(5000)
        creator.createForegroundInfo("保存完了しました", false)
        return Result.success()
    }

    override fun onStopped() {
        creator.createForegroundInfo("保存失敗しました", false)
        super.onStopped()
    }


}
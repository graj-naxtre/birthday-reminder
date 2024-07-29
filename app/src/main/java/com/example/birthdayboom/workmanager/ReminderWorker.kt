package com.example.birthdayboom.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.birthdayboom.utils.NotificationHelper

//class ReminderWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {
//    override fun doWork(): Result {
//        NotificationHelper(context = context).createNotification(
//            inputData.getString("title").toString(),
//            inputData.getString("message").toString()
//        )
//        return Result.success()
//    }
//}
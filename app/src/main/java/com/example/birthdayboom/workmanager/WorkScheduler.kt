package com.example.birthdayboom.workmanager

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

class WorkScheduler @Inject constructor(@ApplicationContext private val context: Context) {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()

    private val workManager = WorkManager.getInstance(context)

    fun scheduleBirthdayReminderWorker() {
        runBlocking(Dispatchers.IO) {
            val workRequest =
                OneTimeWorkRequestBuilder<ReminderWorkManager>()
                    .setConstraints(constraints)
                    .addTag(ReminderWorkManager.REMINDER_WORKER_TAG)
                    .build()

            val result = workManager.enqueue(workRequest)
            Log.i("WorkScheduler", "result ${result.state.value}")
        }
    }
}
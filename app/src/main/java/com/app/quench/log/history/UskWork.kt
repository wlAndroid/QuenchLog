package com.app.quench.log.history

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.quench.log.setting.Audnf

class UskWork(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        Audnf.poDau()
        return Result.success()
    }
}
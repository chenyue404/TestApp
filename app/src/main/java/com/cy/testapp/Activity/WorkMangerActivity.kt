package com.cy.testapp.Activity

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cy.testapp.R
import java.util.concurrent.TimeUnit

/**
 * Created by cy on 2021/11/29.
 */
class WorkMangerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)

        findViewById<Button>(R.id.bt0).setOnClickListener {
            val workRequestBuilder = PeriodicWorkRequestBuilder<TextWorker>(
                1, TimeUnit.HOURS,
                15, TimeUnit.MINUTES
            ).build()
            WorkManager.getInstance(this).enqueue(
                workRequestBuilder
            )
        }
    }

    class TextWorker(appContext: Context, workerParams: WorkerParameters) :
        Worker(appContext, workerParams) {

        override fun doWork(): Result {

            applicationContext.getSharedPreferences("test", Context.MODE_PRIVATE)
                .edit()
                .putString(System.currentTimeMillis().toString(), "1")
                .apply()

            // Indicate whether the work finished successfully with the Result
            return Result.success()
        }
    }
}
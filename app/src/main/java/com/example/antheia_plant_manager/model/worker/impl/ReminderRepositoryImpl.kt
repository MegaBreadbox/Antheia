package com.example.antheia_plant_manager.model.worker.impl import android.Manifest import android.app.NotificationChannel
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.antheia_plant_manager.model.worker.ReminderRepository
import com.example.antheia_plant_manager.model.worker.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

class ReminderRepositoryImpl @Inject constructor(private val workManager: WorkManager): ReminderRepository {


    override fun sendNotification() {

        workManager.enqueueUniquePeriodicWork(
            "plant_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
                .build()
        )

    }
}
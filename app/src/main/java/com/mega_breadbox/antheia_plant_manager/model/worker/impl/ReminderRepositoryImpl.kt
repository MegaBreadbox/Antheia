package com.mega_breadbox.antheia_plant_manager.model.worker.impl
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mega_breadbox.antheia_plant_manager.model.worker.ReminderRepository
import com.mega_breadbox.antheia_plant_manager.model.worker.ReminderWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(private val workManager: WorkManager): ReminderRepository {


    override fun sendNotification() {

        workManager.enqueueUniquePeriodicWork(
            "plant_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
                .build()
        )

    }
}
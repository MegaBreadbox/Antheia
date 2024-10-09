package com.example.antheia_plant_manager.model.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.model.service.AccountService
import com.example.antheia_plant_manager.model.worker.util.reminderNotification
import com.example.antheia_plant_manager.util.Reminder
import com.example.antheia_plant_manager.util.determineReminder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import java.time.LocalDate

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    val plantDatabase: PlantRepository,
    val accountService: AccountService
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val notificationList = mutableListOf<String>()
        Log.d(
            "ReminderWorker",
            "working"
        )

        return withContext(Dispatchers.Default) {
            try {
                plantDatabase.getAllPlants(accountService.currentUserId)
                    .collectLatest { plantList ->
                        plantList.forEach { plant ->
                            when {
                                determineReminder(
                                    plant.waterReminder,
                                    LocalDate.now()
                                ) == Reminder.DuringReminder ->
                                    notificationList.add(plant.name)

                                determineReminder(
                                    plant.repottingReminder,
                                    LocalDate.now()
                                ) == Reminder.DuringReminder ->
                                    notificationList.add(plant.name)

                                determineReminder(
                                    plant.fertilizerReminder,
                                    LocalDate.now()
                                ) == Reminder.DuringReminder ->
                                    notificationList.add(plant.name)
                            }
                        }
                    }
                return@withContext when {
                    notificationList.size == 1 -> {
                        reminderNotification(
                            notificationList[0],
                            context = applicationContext
                        )
                        Result.success()
                    }

                    notificationList.size > 1 -> {
                        reminderNotification(
                            applicationContext.getString(R.string.multiple_plants),
                            context = applicationContext
                        )
                        Result.success()
                    }

                    else -> Result.success()
                }
            } catch(e: Exception) {

             return@withContext Result.failure()
            }
        }
    }

}
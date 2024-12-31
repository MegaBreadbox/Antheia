package com.mega_breadbox.antheia_plant_manager.model.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.model.data.PlantRepository
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.mega_breadbox.antheia_plant_manager.model.worker.util.reminderNotification
import com.mega_breadbox.antheia_plant_manager.util.Reminder
import com.mega_breadbox.antheia_plant_manager.util.determineReminder
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
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

        if(!accountService.isSignedIn()) return Result.failure()

        return withContext(Dispatchers.IO) {
            return@withContext try {
                plantDatabase.getAllPlants(Firebase.auth.currentUser!!.uid)
                    .forEach { plant ->
                        when {
                            determineReminder(
                                plant.waterReminder,
                                LocalDate.now()
                            ) == Reminder.DuringReminder -> {
                                notificationList.add(plant.name)
                            }

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
                when {
                    notificationList.size == 1 -> {
                        reminderNotification(
                            applicationContext.getString(
                                R.string.requires_attention,
                                notificationList[0]
                            ),
                            context = applicationContext
                        )
                        Result.success()
                    }

                    notificationList.size > 1 -> {
                        reminderNotification(
                            applicationContext.getString(R.string.multiple_plants_require_attention),
                            context = applicationContext
                        )
                        Result.success()
                    }

                    else -> {
                        Result.success()
                    }
                }
            } catch(e: Exception) {
                Result.failure()
            }
        }
    }

}
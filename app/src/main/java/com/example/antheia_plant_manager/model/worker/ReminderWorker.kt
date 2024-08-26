package com.example.antheia_plant_manager.model.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val reminderText = inputData.getString(REMINDER_TEXT_KEY)
        if(reminderText != null) {
            return Result.success()
            TODO()
        }
        else { return Result.failure() }
        TODO()
    }

    companion object {
        private val REMINDER_TEXT_KEY = "REMINDER_TEXT_KEY"
    }
}
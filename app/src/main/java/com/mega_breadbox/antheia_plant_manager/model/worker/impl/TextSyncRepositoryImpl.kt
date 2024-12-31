package com.mega_breadbox.antheia_plant_manager.model.worker.impl

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mega_breadbox.antheia_plant_manager.model.worker.TextSyncRepository
import com.mega_breadbox.antheia_plant_manager.model.worker.TextSyncWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TextSyncRepositoryImpl @Inject constructor(private val workManager: WorkManager): TextSyncRepository {

    override fun syncText(plantId: String) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val createDataFromPlantId = Data.Builder()
            .putString("plantId", plantId)
            .build()

        workManager.enqueueUniqueWork(
            plantId,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<TextSyncWorker>()
                .setInitialDelay(INITIAL_DELAY, TimeUnit.MINUTES)
                .setInputData(createDataFromPlantId)
                .setConstraints(constraints)
                .build()

        )

    }
    companion object {
        const val INITIAL_DELAY = 5L
    }
}
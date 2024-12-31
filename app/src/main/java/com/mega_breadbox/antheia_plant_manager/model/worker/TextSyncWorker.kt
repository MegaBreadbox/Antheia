package com.mega_breadbox.antheia_plant_manager.model.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mega_breadbox.antheia_plant_manager.model.data.PlantRepository
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.CloudService
import com.mega_breadbox.antheia_plant_manager.util.toPlantModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class TextSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val plantDatabase: PlantRepository,
    private val accountService: AccountService,
    private val cloudService: CloudService
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val plantId = inputData.getString("plantId")?: ""

        if(!accountService.isSignedIn()) return Result.failure()

        return withContext(Dispatchers.IO) {
            return@withContext try {
                plantDatabase.getPlantOneShot(
                    accountService.currentUserId,
                    plantId
                ).let { plant -> cloudService.updatePlant(plant.toPlantModel()) }
                Result.success()
            } catch(e: Exception) {
                Result.failure()
            }
        }
    }

}
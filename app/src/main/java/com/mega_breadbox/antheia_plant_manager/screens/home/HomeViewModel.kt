package com.mega_breadbox.antheia_plant_manager.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega_breadbox.antheia_plant_manager.model.data.PlantRepository
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.CloudService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.toPlant
import com.mega_breadbox.antheia_plant_manager.model.worker.ReminderRepository
import com.mega_breadbox.antheia_plant_manager.util.SUBSCRIBE_DELAY
import com.mega_breadbox.antheia_plant_manager.util.toPlantModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val plantDatabase: PlantRepository,
    private val reminderWorker: ReminderRepository,
    private val accountService: AccountService,
    private val cloudService: CloudService,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {



    val locationsList = plantDatabase.getPlantLocations(accountService.currentUserId)
        .onStart { syncUserData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = emptyList()
        )

    fun sendNotification() {
        reminderWorker.sendNotification()
    }

    private fun syncUserData() {
        viewModelScope.launch(ioDispatcher) {
            if (
                plantDatabase.getAllPlants(accountService.currentUserId).isEmpty()
            ) {
                plantDatabase.addPlants(
                    cloudService.getAllUserData().map { list ->
                        list.toPlant()
                    }
                )
            } else {
            }
        }
    }
}


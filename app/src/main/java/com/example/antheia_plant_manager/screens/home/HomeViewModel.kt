package com.example.antheia_plant_manager.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.example.antheia_plant_manager.model.service.firestore.CloudService
import com.example.antheia_plant_manager.model.service.firestore.toPlant
import com.example.antheia_plant_manager.model.worker.ReminderRepository
import com.example.antheia_plant_manager.util.SUBSCRIBE_DELAY
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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



    val locationsList = plantDatabase.getPlantLocations(Firebase.auth.currentUser!!.uid)
        .onStart { syncUserData() }
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = emptyList()
        )

    private fun syncUserData() {
        viewModelScope.launch(ioDispatcher) {
            if (
                Firebase.auth.currentUser?.isAnonymous == false &&
                plantDatabase.getAllPlants(Firebase.auth.currentUser!!.uid).isEmpty()
            ) {
                plantDatabase.addPlants(
                    cloudService.getAllUserData().map { list ->
                        list.toPlant()
                    }
                )
                reminderWorker.sendNotification()
            }
        }
    }
}


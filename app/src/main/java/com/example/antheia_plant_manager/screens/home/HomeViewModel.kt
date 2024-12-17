package com.example.antheia_plant_manager.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.example.antheia_plant_manager.model.service.firestore.CloudService
import com.example.antheia_plant_manager.model.service.firestore.toPlant
import com.example.antheia_plant_manager.util.SUBSCRIBE_DELAY
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
    accountService: AccountService,
    private val cloudService: CloudService,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val locationsList = plantDatabase.getPlantLocations(accountService.currentUserId)
        .onStart {
            viewModelScope.launch(ioDispatcher) {
                syncUserData()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = emptyList()
        )

    private suspend fun syncUserData() {
        Log.d("HomeViewModel", "syncUserData: called")
        plantDatabase.addPlants(
            cloudService.getAllUserData().map { list ->
                Log.d("HomeViewModel", "syncUserData: $list")
                list.toPlant().also {
                    Log.d("HomeViewModel", "syncUserData: ${list.toPlant()}")
                }
            }
        )
    }
}


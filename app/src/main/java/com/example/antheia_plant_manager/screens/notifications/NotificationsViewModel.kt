package com.example.antheia_plant_manager.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.model.service.AccountService
import com.example.antheia_plant_manager.util.PlantAlert
import com.example.antheia_plant_manager.util.SUBSCRIBE_DELAY
import com.example.antheia_plant_manager.util.requiresAttention
import com.example.antheia_plant_manager.util.toPlantAlert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val plantDatabase: PlantRepository,
    private val accountService: AccountService
): ViewModel() {

    val plantAlerts: StateFlow<List<PlantAlert>> = plantDatabase.getAllPlantsFlow(accountService.currentUserId)
        .map { plants -> plants.filter { it.requiresAttention() } }
        .map { plants -> plants.map { it.toPlantAlert() }}
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = emptyList()
        )
}
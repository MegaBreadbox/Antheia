package com.example.antheia_plant_manager.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.model.service.AccountService
import com.example.antheia_plant_manager.util.SUBSCRIBE_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    plantDatabase: PlantRepository,
    accountService: AccountService
): ViewModel() {

    val locationsList = plantDatabase.getPlantLocations(accountService.currentUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = emptyList()
        )
}


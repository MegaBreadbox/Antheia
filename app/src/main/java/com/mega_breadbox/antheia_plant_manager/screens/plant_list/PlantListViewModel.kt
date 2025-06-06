package com.mega_breadbox.antheia_plant_manager.screens.plant_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mega_breadbox.antheia_plant_manager.model.data.PlantRepository
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.mega_breadbox.antheia_plant_manager.nav_routes.PlantList
import com.mega_breadbox.antheia_plant_manager.util.DATE_CHECK_DELAY
import com.mega_breadbox.antheia_plant_manager.util.PlantAlert
import com.mega_breadbox.antheia_plant_manager.util.SUBSCRIBE_DELAY
import com.mega_breadbox.antheia_plant_manager.util.determineReminder
import com.mega_breadbox.antheia_plant_manager.util.toPlantAlert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlantListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    plantDatabase: PlantRepository,
    accountService: AccountService
): ViewModel() {

    val location = savedStateHandle.toRoute<PlantList>().location

    private val currentDate: Flow<LocalDate> = flow {
        while(true) {
            emit(LocalDate.now())
            delay(DATE_CHECK_DELAY)
        }
    }

    private val plantList = plantDatabase.getPlants(
        userId = accountService.currentUserId,
        location = location
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = emptyList()
        )

    val plantAlerts: StateFlow<List<PlantAlert>> = plantList
        .combine(currentDate) { plantList, currentDate ->
            plantList.map { plant ->
                plant.toPlantAlert(
                    waterAlert = determineReminder(plant.waterReminder, currentDate),
                    repottingAlert = determineReminder(plant.repottingReminder, currentDate),
                    fertilizerAlert = determineReminder(plant.fertilizerReminder, currentDate)
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = emptyList()
        )


}





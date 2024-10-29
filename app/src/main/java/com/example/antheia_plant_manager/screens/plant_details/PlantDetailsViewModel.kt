package com.example.antheia_plant_manager.screens.plant_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.model.service.AccountService
import com.example.antheia_plant_manager.model.worker.ReminderRepository
import com.example.antheia_plant_manager.model.worker.ReminderWorker
import com.example.antheia_plant_manager.nav_routes.PlantDetails
import com.example.antheia_plant_manager.screens.plant_details.util.ButtonType
import com.example.antheia_plant_manager.util.ComposeText
import com.example.antheia_plant_manager.util.DATE_CHECK_DELAY
import com.example.antheia_plant_manager.util.PlantAlert
import com.example.antheia_plant_manager.util.PlantEntry
import com.example.antheia_plant_manager.util.ReminderFrequency
import com.example.antheia_plant_manager.util.SUBSCRIBE_DELAY
import com.example.antheia_plant_manager.util.determineReminder
import com.example.antheia_plant_manager.util.toPlant
import com.example.antheia_plant_manager.util.toPlantAlert
import com.example.antheia_plant_manager.util.toPlantEntry
import com.example.antheia_plant_manager.util.updateReminderDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlantDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val plantDatabase: PlantRepository,
    private val accountService: AccountService,
    private val reminderWorker: ReminderRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val currentDate: Flow<LocalDate> = flow {
        while(true) {
            emit(LocalDate.now())
            delay(DATE_CHECK_DELAY)
        }
    }

    private val plantId = savedStateHandle.toRoute<PlantDetails>().plantId

    private val _uiState = MutableStateFlow(PlantDetailsUiState())
    val uiState = _uiState.asStateFlow()


    fun updateSelectedTab(tabIndex: Int) {
        _uiState.update {
            it.copy(selectedTab = tabIndex)
        }
    }


    val plant: StateFlow<Plant> = plantDatabase.getPlant(accountService.currentUserId, plantId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = PlantEntry().toPlant()
        )

    val plantAlert: StateFlow<PlantAlert> = plant
        .combine(currentDate) { plant, currentDate ->
            plant.toPlantAlert(
                waterAlert = determineReminder(
                    plant.waterReminder,
                    currentDate
                ),
                repottingAlert = determineReminder(
                    plant.repottingReminder,
                    currentDate
                ),
                fertilizerAlert = determineReminder(
                    plant.fertilizerReminder,
                    currentDate
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = PlantAlert()
        )

    //Summary Tab **********************************************************************************

    fun taskButtonClicked(buttonType: ButtonType) {
        updatePlant(buttonType)
        updateLatestButtonClicked(buttonType)
        updateButtonEffectAlpha()
    }

    private fun updateLatestButtonClicked(buttonType: ButtonType) {
        _uiState.update {
            it.copy(latestButtonClicked = buttonType)
        }
    }

    private fun updateButtonEffectAlpha() {
        _uiState.update {
            it.copy(buttonEffectAlpha = 0.4F)
        }
        viewModelScope.launch {
            while (_uiState.value.buttonEffectAlpha >= 0) {
                delay(20)
                _uiState.update {
                    it.copy(buttonEffectAlpha = it.buttonEffectAlpha - 0.01F)
                }
            }
        }
    }

    private fun updatePlant(buttonType: ButtonType) {
        when(buttonType) {
            ButtonType.WATER -> {
                viewModelScope.launch {
                    plantDatabase.updatePlant(
                        plant.value.copy(
                            waterReminder = plant.value.waterReminder.updateReminderDate()
                        )
                    )
                }
            }
            ButtonType.REPOT -> {
                viewModelScope.launch {
                    plantDatabase.updatePlant(
                        plant.value.copy(
                            repottingReminder = plant.value.repottingReminder.updateReminderDate()
                        )
                    )
                }
            }
            ButtonType.FERTILIZE -> {
                viewModelScope.launch {
                    plantDatabase.updatePlant(
                        plant.value.copy(
                            repottingReminder = plant.value.repottingReminder.updateReminderDate()
                        )
                    )
                }
            }
        }
    }

    //Settings Tab *********************************************************************************


    private val _plantEntryTemp = MutableStateFlow(PlantEntry())
    private val _plantEntry = MutableStateFlow(PlantEntry())
    val plantEntry = _plantEntryTemp
        .onStart { initialPlantEntry() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = PlantEntry()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val suggestions =
        _plantEntryTemp
            .filter { it.location.isNotEmpty() }
            .flatMapLatest { currentPlant ->
                plantDatabase.getPlantLocationSuggestions(currentPlant.location)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY), emptyList())

    fun updateLocation(newLocation: String) {
        _plantEntry.update { it.copy(location = newLocation) }
        _plantEntryTemp.update { it.copy(location = newLocation) }
    }

    fun updateName(newName: String) {
        _plantEntry.update { it.copy(name = newName) }
        _plantEntryTemp.update { it.copy(name = newName) }
    }

    private fun initialPlantEntry() {
        viewModelScope.launch {
            val latestPlantEntry = plantDatabase.getPlantOneShot(accountService.currentUserId, plantId).toPlantEntry()

            _plantEntryTemp.update {
                latestPlantEntry
            }
            _plantEntry.update {
               latestPlantEntry
            }
        }
    }

    fun updateSelectedReminder(reminderType: ReminderFrequency) {
        updateDateMap()
        _uiState.update {
            it.copy(currentSelectedReminder = reminderType)
        }
    }


    fun clearSelectedReminder() {
        _uiState.update {
            it.copy(
                currentSelectedReminder = null,
            )
        }
        _plantEntryTemp.update {
            _plantEntry.value
        }
    }

    fun updateTempReminderOfPlant(completeReminderText: String, reminderType: ReminderFrequency) {
        when(reminderType) {
            ReminderFrequency.WATERREMINDER -> { _plantEntryTemp.update { it.copy(waterReminder = completeReminderText) } }
            ReminderFrequency.REPOTTINGREMINDER -> { _plantEntryTemp.update { it.copy(repottingReminder = completeReminderText) } }
            ReminderFrequency.FERTILIZERREMINDER -> { _plantEntryTemp.update { it.copy(fertilizerReminder = completeReminderText) } }
        }
    }

    fun updateReminderOfPlant(completeReminderText: String, reminderType: ReminderFrequency) {
        when(reminderType) {
            ReminderFrequency.WATERREMINDER -> { _plantEntry.update { it.copy(waterReminder = completeReminderText) } }
            ReminderFrequency.REPOTTINGREMINDER -> { _plantEntry.update { it.copy(repottingReminder = completeReminderText) } }
            ReminderFrequency.FERTILIZERREMINDER -> { _plantEntry.update { it.copy(fertilizerReminder = completeReminderText) } }
        }
    }


    private fun updateDateMap() {
        val currentDate = LocalDate.now()
        _uiState.update {
            it.copy(dateMap =
                mapOf(
                    ComposeText(R.string.every_day) to currentDate.plusDays(1).toString(),
                    ComposeText(R.string.every_2_days) to currentDate.plusDays(2).toString(),
                    ComposeText(R.string.every_3_days) to currentDate.plusDays(3).toString(),
                    ComposeText(R.string.every_week) to currentDate.plusWeeks(1).toString(),
                    ComposeText(R.string.every_2_weeks) to currentDate.plusWeeks(2).toString(),
                    ComposeText(R.string.every_3_weeks) to currentDate.plusWeeks(3).toString(),
                    ComposeText(R.string.every_month) to currentDate.plusMonths(1).toString(),
                    ComposeText(R.string.every_6_months) to currentDate.plusMonths(6).toString(),
                    ComposeText(R.string.every_year) to currentDate.plusYears(1).toString(),
                    ComposeText(R.string.every_2_years) to currentDate.plusYears(2).toString(),
                    ComposeText(R.string.every_4_years) to currentDate.plusYears(4).toString(),
                )
            )
        }
    }
    val plantHasBeenUpdated = plant.combine(_plantEntryTemp) { plant, plantEntry ->
        plant != plantEntry.toPlant() &&
                plantEntry.name.isNotEmpty() &&
                plantEntry.location.isNotEmpty() &&
                plantEntry.waterReminder.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
        initialValue = false
    )

    fun savePlant() {
        viewModelScope.launch {
            plantDatabase.updatePlant(_plantEntry.value.toPlant())
            reminderWorker.sendNotification()
        }
    }

    //Notes Tab ************************************************************************************


    fun updateNotesText(newText: String) {
        _plantEntryTemp.update {
            it.copy(notes = newText)
        }
        _plantEntry.update {
            it.copy(notes = newText)
        }
        viewModelScope.launch(ioDispatcher) {
            plantDatabase.updatePlant(_plantEntry.value.toPlant().copy(notes = newText))
        }
    }


}

data class PlantDetailsUiState(
    val selectedTab: Int = 0,
    val latestButtonClicked: ButtonType = ButtonType.WATER,
    val buttonEffectAlpha: Float = 0F,
    val dateMap: Map<ComposeText, String> = mapOf(),
    val currentSelectedReminder: ReminderFrequency? = null,
)
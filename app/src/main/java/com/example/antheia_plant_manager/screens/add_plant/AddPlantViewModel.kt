package com.example.antheia_plant_manager.screens.add_plant

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.util.ComposeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(): ViewModel() {

    private val _currentPlant = MutableStateFlow(PlantEntry())
    val currentPlant = _currentPlant.asStateFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    private val currentDate: Flow<LocalDate> = flow {
        emit(LocalDate.now())
    }

    var plantName by mutableStateOf("")
        private set

    var locationName by mutableStateOf("")
        private set

    fun updatePlantName(name: String) {
        plantName = name
        _currentPlant.update {
            it.copy(name = name)
        }
    }

    fun updateLocation(location: String) {
        locationName = location
        _currentPlant.update {
            it.copy(location = location)
        }
    }

    fun updateAdvancedSettings() {
        _uiState.update {
            it.copy(isAdvancedSettingsEnabled = !it.isAdvancedSettingsEnabled)
        }
    }

    fun isDialogCurrentlyActive() : Boolean {
        return _uiState.value.isWaterFrequencyDialogActive ||
                _uiState.value.isRepottingFrequencyDialogActive ||
                _uiState.value.isFertilizerFrequencyDialogActive
    }

    fun updateWaterFrequencyDialog() {
        receiveLatestFrequencyDate()
         _uiState.update {
             it.copy(
                 isWaterFrequencyDialogActive = !it.isWaterFrequencyDialogActive,
                 selectedReminder = _currentPlant.value.waterReminder
             )
         }
    }

    fun updateRepottingFrequencyDialog() {
        receiveLatestFrequencyDate()
        _uiState.update {
            it.copy(
                isRepottingFrequencyDialogActive = !it.isRepottingFrequencyDialogActive,
                selectedReminder = _currentPlant.value.repottingReminder
            )
        }
    }

    fun updateFertilizerFrequencyDialog() {
        receiveLatestFrequencyDate()
        _uiState.update {
            it.copy(
                isFertilizerFrequencyDialogActive = !it.isFertilizerFrequencyDialogActive,
                selectedReminder = _currentPlant.value.fertilizerReminder
            )
        }
    }

    fun updateSelectedReminder(nextReminder: String) {
        _uiState.update {
            it.copy(selectedReminder = nextReminder)
        }
    }


    fun reminderFrequency(reminder: ReminderFrequency): String {
        return when (reminder) {
            ReminderFrequency.WATERREMINDER -> _currentPlant.value.waterReminder.split("+")[0]
            ReminderFrequency.REPOTTINGREMINDER -> _currentPlant.value.repottingReminder.split("+")[0]
            ReminderFrequency.FERTILIZERREMINDER -> _currentPlant.value.fertilizerReminder.split("+")[0]
        }
    }

    fun updateFrequencyDate(nextReminder: String) {
        when {
            _uiState.value.isWaterFrequencyDialogActive -> _currentPlant.update { it.copy(waterReminder = nextReminder) }
            _uiState.value.isRepottingFrequencyDialogActive -> _currentPlant.update { it.copy(repottingReminder = nextReminder) }
            _uiState.value.isFertilizerFrequencyDialogActive -> _currentPlant.update { it.copy(fertilizerReminder = nextReminder) }
        }
    }

    fun removeFrequencyDialog() {
        _uiState.update {
            it.copy(
                isRepottingFrequencyDialogActive = false,
                isFertilizerFrequencyDialogActive = false,
                isWaterFrequencyDialogActive = false,
                selectedReminder = ""
            )
        }
    }

    private fun receiveLatestFrequencyDate(){
        viewModelScope.launch {
            currentDate.collectLatest {
                _uiState.update { state ->
                    state.copy(dateMap =
                        mapOf(
                            ComposeText(R.string.every_day) to it.plusDays(1).toString(),
                            ComposeText(R.string.every_2_days) to it.plusDays(2).toString(),
                            ComposeText(R.string.every_3_days) to it.plusDays(3).toString(),
                            ComposeText(R.string.every_week) to it.plusWeeks(1).toString(),
                            ComposeText(R.string.every_2_weeks) to it.plusWeeks(2).toString(),
                            ComposeText(R.string.every_3_weeks) to it.plusWeeks(3).toString(),
                            ComposeText(R.string.every_month) to it.plusMonths(1).toString(),
                            ComposeText(R.string.every_6_months) to it.plusMonths(6).toString(),
                            ComposeText(R.string.every_year) to it.plusYears(1).toString(),
                            ComposeText(R.string.every_2_years) to it.plusYears(2).toString(),
                            ComposeText(R.string.every_4_years) to it.plusYears(4).toString(),
                        )
                    )
                }
            }
        }
    }
}

data class PlantEntry(
    val plantId: Int = 0,
    val name: String = "",
    val location: String = "",
    val waterReminder: String = "", //formated, frequency+date
    val repottingReminder: String = "",
    val fertilizerReminder: String = "",
)

data class UiState(
    val isWaterFrequencyDialogActive: Boolean = false,
    val isRepottingFrequencyDialogActive: Boolean = false,
    val isFertilizerFrequencyDialogActive: Boolean = false,
    val selectedReminder: String = "",
    val dateMap: Map<ComposeText, String> = mapOf(),
    val isAdvancedSettingsEnabled: Boolean = false
)

enum class ReminderFrequency{
    WATERREMINDER, REPOTTINGREMINDER, FERTILIZERREMINDER
}
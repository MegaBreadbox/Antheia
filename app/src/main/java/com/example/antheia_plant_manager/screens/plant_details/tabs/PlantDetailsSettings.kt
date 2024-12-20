package com.example.antheia_plant_manager.screens.plant_details.tabs

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.add_plant.FrequencyDialog
import com.example.antheia_plant_manager.screens.add_plant.LocationSuggestions
import com.example.antheia_plant_manager.util.ComposeText
import com.example.antheia_plant_manager.util.PlantEntry
import com.example.antheia_plant_manager.util.ReminderFrequency
import com.example.antheia_plant_manager.util.determineReminderText
import com.example.antheia_plant_manager.util.getReminderFrequency
import com.example.antheia_plant_manager.util.outlinedDisabledColorToActive
import com.example.antheia_plant_manager.util.outlinedTextFieldClickModifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class,
)
@Composable
fun PlantDetailsSettings(
    selectedReminder: ReminderFrequency?,
    inputMap: Map<ComposeText, String>,
    currentPlant: PlantEntry,
    locationSuggestions: List<String>,
    updateName: (String) -> Unit,
    updateLocation: (String) -> Unit,
    validatePlant: Boolean,
    onDropdownClick: (ReminderFrequency) -> Unit,
    onRadioClick: (String, ReminderFrequency) -> Unit,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var locationFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val verticalScroll = rememberScrollState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { onSaveClick() }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(state = verticalScroll)
            .imePadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.large_padding))
        ) {
            OutlinedTextField(
                value = currentPlant.name,
                onValueChange = { updateName(it) },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                placeholder = { Text(stringResource(R.string.plant_name)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_compact))

            )

            Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))

            ExposedDropdownMenuBox(
                expanded = locationFocus,
                onExpandedChange = {
                },
                modifier = modifier
            ) {
                OutlinedTextField(
                    value = currentPlant.location,
                    onValueChange = {
                        updateLocation(it)
                    },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    placeholder = { Text(stringResource(R.string.location)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                    modifier = modifier
                        .width(dimensionResource(id = R.dimen.textfield_size_compact))
                        .onFocusChanged { locationFocus = it.isFocused }
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = locationSuggestions.isNotEmpty() && locationFocus,
                    onDismissRequest = { locationFocus = false },
                    modifier = modifier
                ) {
                    LocationSuggestions(
                        locationSuggestions = locationSuggestions,
                        currentLocation = currentPlant.location,
                        onEntryClick = {
                            updateLocation(it)
                        }
                    )
                }
            }
        }

        FrequencyDropDown(
            reminderType = ReminderFrequency.WATERREMINDER,
            frequencyReminderString = currentPlant.determineReminderText(ReminderFrequency.WATERREMINDER)
                .getReminderFrequency(),
            onDropdownClick = { onDropdownClick(ReminderFrequency.WATERREMINDER) },
            accompanyingTextId = R.string.watering_frequency,
            accompanyingDescriptionId = R.string.water_frequency_dropdown
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        FrequencyDropDown(
            reminderType = ReminderFrequency.REPOTTINGREMINDER,
            frequencyReminderString = currentPlant.determineReminderText(ReminderFrequency.REPOTTINGREMINDER)
                .getReminderFrequency(),
            onDropdownClick = { onDropdownClick(ReminderFrequency.REPOTTINGREMINDER) },
            accompanyingTextId = R.string.repotting_frequency,
            accompanyingDescriptionId = R.string.repotting_frequency_dropdown
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        FrequencyDropDown(
            reminderType = ReminderFrequency.FERTILIZERREMINDER,
            frequencyReminderString = currentPlant.determineReminderText(ReminderFrequency.FERTILIZERREMINDER)
                .getReminderFrequency(),
            onDropdownClick = { onDropdownClick(ReminderFrequency.FERTILIZERREMINDER) },
            accompanyingTextId = R.string.fertilizing_frequency,
            accompanyingDescriptionId = R.string.fertilizing_frequency_dropdown
        )
        if (selectedReminder != null) {
            Box() {
                FrequencyDialog(
                    inputMap = inputMap,
                    selectedReminder = currentPlant.determineReminderText(selectedReminder)
                        .getReminderFrequency(),
                    onConfirmClick = {
                        onConfirmClick()
                    },
                    onDismissClick = onDismissClick,
                    onRadioClick = {
                        onRadioClick(
                            it,
                            selectedReminder
                        )
                    }
                )
            }
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Button(
            enabled = validatePlant,
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    onSaveClick()
                }
            },
            modifier = modifier
                .width(dimensionResource(id = R.dimen.button_wide))
                .padding(top = dimensionResource(id = R.dimen.big_padding))
        ) {
            Text(stringResource(R.string.save))
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.big_padding)))
        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            modifier = modifier
                .width(dimensionResource(id = R.dimen.button_wide))
        ) {
            Text(stringResource(R.string.delete_plant))
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
        Spacer(modifier = modifier
            .consumeWindowInsets(WindowInsets.ime)
        )
    }
}

@Composable
fun FrequencyDropDown(
    reminderType: ReminderFrequency,
    frequencyReminderString: String,
    onDropdownClick: (ReminderFrequency) -> Unit,
    accompanyingTextId: Int,
    accompanyingDescriptionId: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(accompanyingTextId),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
        )
        Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
        OutlinedTextField(
            value = frequencyReminderString,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            colors = outlinedDisabledColorToActive(),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(accompanyingDescriptionId)
                )
            },
            modifier = outlinedTextFieldClickModifier(onClick = { onDropdownClick(reminderType) })
        )
    }
}
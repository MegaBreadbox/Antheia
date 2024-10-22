package com.example.antheia_plant_manager.screens.plant_details.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.add_plant.FrequencyDialog
import com.example.antheia_plant_manager.util.ComposeText
import com.example.antheia_plant_manager.util.PlantEntry
import com.example.antheia_plant_manager.util.ReminderFrequency
import com.example.antheia_plant_manager.util.determineReminderText
import com.example.antheia_plant_manager.util.getReminderFrequency
import com.example.antheia_plant_manager.util.outlinedDisabledColorToActive
import com.example.antheia_plant_manager.util.outlinedTextFieldClickModifier

@Composable
fun PlantDetailsSettings(
    selectedReminder: ReminderFrequency?,
    inputMap: Map<ComposeText, String>,
    currentPlant: PlantEntry,
    onDropdownClick: (ReminderFrequency) -> Unit,
    onRadioClick: (String, ReminderFrequency) -> Unit,
    onConfirmClick: (String, ReminderFrequency) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
    FrequencyDropDown(
        reminderType = ReminderFrequency.WATERREMINDER,
        frequencyReminderString = currentPlant.determineReminderText(ReminderFrequency.WATERREMINDER).getReminderFrequency(),
        onDropdownClick = { onDropdownClick(ReminderFrequency.WATERREMINDER) },
        accompanyingTextId = R.string.watering_frequency,
        accompanyingDescriptionId = R.string.water_frequency_dropdown
    )
    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
    FrequencyDropDown(
        reminderType = ReminderFrequency.REPOTTINGREMINDER,
        frequencyReminderString = currentPlant.determineReminderText(ReminderFrequency.REPOTTINGREMINDER).getReminderFrequency(),
        onDropdownClick = { onDropdownClick(ReminderFrequency.REPOTTINGREMINDER) },
        accompanyingTextId = R.string.repotting_frequency,
        accompanyingDescriptionId = R.string.repotting_frequency_dropdown
    )
    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
    FrequencyDropDown(
        reminderType = ReminderFrequency.FERTILIZERREMINDER,
        frequencyReminderString = currentPlant.determineReminderText(ReminderFrequency.FERTILIZERREMINDER).getReminderFrequency(),
        onDropdownClick = { onDropdownClick(ReminderFrequency.FERTILIZERREMINDER) },
        accompanyingTextId = R.string.fertilizing_frequency,
        accompanyingDescriptionId = R.string.fertilizing_frequency_dropdown
    )
    if(selectedReminder != null) {
        Box() {
            FrequencyDialog(
                inputMap = inputMap,
                selectedReminder = currentPlant.determineReminderText(selectedReminder).getReminderFrequency(),
                onConfirmClick = { onConfirmClick(it, selectedReminder) },
                onDismissClick = onDismissClick,
                onRadioClick = { onRadioClick(it, selectedReminder) }
            )
        }
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
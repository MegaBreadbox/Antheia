package com.example.antheia_plant_manager.screens.add_plant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.data.mock.PlantRepositoryImplMock
import com.example.antheia_plant_manager.screens.add_plant.util.AddPlantConstant
import com.example.antheia_plant_manager.screens.add_plant.util.ReminderFrequency
import com.example.antheia_plant_manager.util.ComposeText
import com.example.antheia_plant_manager.util.OutlinedDisabledColorToActive
import com.example.antheia_plant_manager.util.OutlinedTextFieldClickModifier
import com.example.compose.AntheiaplantmanagerTheme

@Composable
fun AddPlantScreen(
    windowSize: WindowWidthSizeClass,
    navigateBack: () -> Unit
) {
    when(windowSize) {
        WindowWidthSizeClass.Compact -> {
            PlantFormCompact(navigateBack)
        }
        WindowWidthSizeClass.Medium -> {
            PlantFormMedium(navigateBack)
        }
    }
}

@Composable
fun PlantFormCompact(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddPlantViewModel = hiltViewModel<AddPlantViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val scroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.large_padding))
            .verticalScroll(scroll)
    ) {
        OutlinedTextField(
            value = viewModel.plantName,
            onValueChange = { viewModel.updatePlantName(it) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            placeholder = { Text(stringResource(R.string.plant_name)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_compact))

        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        OutlinedTextField(
            value = viewModel.locationName,
            onValueChange = { viewModel.updateLocation(it) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            placeholder = { Text(stringResource(R.string.location)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
                ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            singleLine = true,
            modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_compact))
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.watering_frequency),
                modifier = modifier.weight(1f)
            )
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            OutlinedTextField(
                value = viewModel.reminderFrequency(ReminderFrequency.WATERREMINDER),
                onValueChange = {},
                enabled = false,
                readOnly = true,
                colors = OutlinedDisabledColorToActive(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.water_frequency_dropdown)
                    )
                },
                modifier = OutlinedTextFieldClickModifier(onClick = { viewModel.updateWaterFrequencyDialog() })
            )
        }
        if(viewModel.isDialogCurrentlyActive()) {
            Box() {
                FrequencyDialog(
                    inputMap = uiState.value.dateMap,
                    selectedReminder = uiState.value.selectedReminder,
                    onConfirmClick = { viewModel.updateFrequencyDate(it) },
                    onDismissClick = { viewModel.removeFrequencyDialog() },
                    onRadioClick = { viewModel.updateSelectedReminder(it) }
                )
            }
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(R.string.advanced_settings))
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            Switch(
                checked = uiState.value.isAdvancedSettingsEnabled,
                onCheckedChange = { viewModel.updateAdvancedSettings() }
            )
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.small_padding)))
        HorizontalDivider()
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        AnimatedVisibility(
            visible = uiState.value.isAdvancedSettingsEnabled,
            enter =
                fadeIn(animationSpec = tween(durationMillis = AddPlantConstant.FADE_DURATION)) +
                expandVertically(animationSpec = tween(durationMillis = AddPlantConstant.EXPAND_SHRINK_DURATION)),
            exit =
                fadeOut(animationSpec = tween(durationMillis = AddPlantConstant.FADE_DURATION)) +
                shrinkVertically(animationSpec = tween(durationMillis = AddPlantConstant.EXPAND_SHRINK_DURATION))
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.repotting_frequency),
                        modifier = modifier
                            .weight(1f)
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.REPOTTINGREMINDER),
                        onValueChange = {},
                        enabled = false,
                        readOnly = true,
                        colors = OutlinedDisabledColorToActive(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.repotting_frequency_dropdown)
                            )
                        },
                        modifier = OutlinedTextFieldClickModifier(onClick = { viewModel.updateRepottingFrequencyDialog() })
                    )
                }

                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.fertilizing_frequency),
                        modifier = modifier.weight(1f)
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.FERTILIZERREMINDER),
                        onValueChange = {},
                        enabled = false,
                        colors = OutlinedDisabledColorToActive(),
                        readOnly = true,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.fertilizing_frequency_dropdown)
                            )
                        },
                        modifier = OutlinedTextFieldClickModifier(onClick = { viewModel.updateFertilizerFrequencyDialog() })
                    )
                }
                Spacer(
                    modifier = modifier
                        .weight(1f)
                )
            }
        }
        Button(
            enabled = viewModel.validatePlant(),
            onClick = {
                viewModel.savePlant()
                navigateBack()
            },
            modifier = modifier
                .width(dimensionResource(id = R.dimen.button_wide))
                .padding(top = dimensionResource(id = R.dimen.large_padding))
        ) {
            Text(stringResource(R.string.save))
        }
    }
}

@Composable
fun PlantFormMedium(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddPlantViewModel = hiltViewModel<AddPlantViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val scroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.large_padding))
            .verticalScroll(scroll)
    ) {
        OutlinedTextField(
            value = viewModel.plantName,
            onValueChange = { viewModel.updatePlantName(it) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            placeholder = { Text(stringResource(R.string.plant_name)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_compact))

        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        OutlinedTextField(
            value = viewModel.locationName,
            onValueChange = { viewModel.updateLocation(it) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            placeholder = { Text(stringResource(R.string.location)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            singleLine = true,
            modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_compact))
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.watering_frequency),
                modifier = modifier
            )
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            OutlinedTextField(
                value = viewModel.reminderFrequency(ReminderFrequency.WATERREMINDER),
                onValueChange = {},
                enabled = false,
                readOnly = true,
                colors = OutlinedDisabledColorToActive(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.water_frequency_dropdown)
                    )
                },
                modifier = OutlinedTextFieldClickModifier(onClick = { viewModel.updateWaterFrequencyDialog() })
            )
        }
        if(viewModel.isDialogCurrentlyActive()) {
            Box() {
                FrequencyDialog(
                    inputMap = uiState.value.dateMap,
                    selectedReminder = uiState.value.selectedReminder,
                    onConfirmClick = { viewModel.updateFrequencyDate(it) },
                    onDismissClick = { viewModel.removeFrequencyDialog() },
                    onRadioClick = { viewModel.updateSelectedReminder(it) }
                )
            }
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(R.string.advanced_settings))
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            Switch(
                checked = uiState.value.isAdvancedSettingsEnabled,
                onCheckedChange = { viewModel.updateAdvancedSettings() }
            )
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.small_padding)))
        HorizontalDivider()
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        AnimatedVisibility(
            visible = uiState.value.isAdvancedSettingsEnabled,
            enter =
            fadeIn(animationSpec = tween(durationMillis = AddPlantConstant.FADE_DURATION)) +
                    expandVertically(animationSpec = tween(durationMillis = AddPlantConstant.EXPAND_SHRINK_DURATION)),
            exit =
            fadeOut(animationSpec = tween(durationMillis = AddPlantConstant.FADE_DURATION)) +
                    shrinkVertically(animationSpec = tween(durationMillis = AddPlantConstant.EXPAND_SHRINK_DURATION))
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.repotting_frequency),
                        modifier = modifier
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.REPOTTINGREMINDER),
                        onValueChange = {},
                        enabled = false,
                        readOnly = true,
                        colors = OutlinedDisabledColorToActive(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.repotting_frequency_dropdown)
                            )
                        },
                        modifier = OutlinedTextFieldClickModifier(onClick = { viewModel.updateRepottingFrequencyDialog() })
                    )
                }
                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.fertilizing_frequency),
                        modifier = modifier
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.FERTILIZERREMINDER),
                        onValueChange = {},
                        enabled = false,
                        colors = OutlinedDisabledColorToActive(),
                        readOnly = true,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.fertilizing_frequency_dropdown)
                            )
                        },
                        modifier = OutlinedTextFieldClickModifier(onClick = { viewModel.updateFertilizerFrequencyDialog() })
                    )
                }
                Spacer(
                    modifier = modifier
                        .weight(1f)
                )
            }
        }
        Button(
            enabled = viewModel.validatePlant(),
            onClick = {
                viewModel.savePlant()
                navigateBack()
            },
            modifier = modifier
                .width(dimensionResource(id = R.dimen.button_wide))
                .padding(top = dimensionResource(id = R.dimen.big_padding))
        ) {
            Text(stringResource(R.string.save))
        }
    }
}
@Composable
fun FrequencyDialog(
    inputMap: Map<ComposeText, String>,
    selectedReminder: String,
    onRadioClick: (String) -> Unit,
    onConfirmClick: (String) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState()
    Dialog(
        onDismissRequest = onDismissClick,
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape)))
                .height(dimensionResource(id = R.dimen.dialog_height))
        ) {
            Column(
                modifier = modifier
                    .weight(1F)
                    .verticalScroll(scroll)
            ) {
                inputMap.forEach {
                    val reminderFrequencyAndDate = "${ it.key.asString()}+${ it.value }"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .padding(dimensionResource(id = R.dimen.dialog_padding))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape)))
                            .selectable(
                                selected = reminderFrequencyAndDate == selectedReminder,
                                onClick = { onRadioClick(reminderFrequencyAndDate) },
                            )
                    ) {
                        RadioButton(
                            selected = reminderFrequencyAndDate == selectedReminder,
                            onClick = { onRadioClick(reminderFrequencyAndDate) })
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(text = it.key.asString())
                            Text(
                                text = it.value,
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.dialog_divider_padding))
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dialog_padding))
            ) {
                TextButton(onClick =  onDismissClick) {
                    Text(stringResource(R.string.dismiss))
                }
                TextButton(
                    onClick = {
                        onConfirmClick(selectedReminder)
                        onDismissClick()
                    },
                    enabled = selectedReminder.isNotEmpty()
                ) {
                    Text(stringResource(R.string.confirm))
                }
            }
        }
    }
}

@Composable
fun CloseIcon(
    onCloseClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.large_padding))
    ) {
        IconButton(onClick = onCloseClick) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.exit_form)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun AddPlantScreenCompactPreview() {
    AntheiaplantmanagerTheme {
        val addPlantViewModel = AddPlantViewModel(plantDatabase = PlantRepositoryImplMock())
        PlantFormCompact(
            viewModel = addPlantViewModel,
            navigateBack = {}
        )
    }
}
@Composable
@Preview
fun AddPlantScreenMediumPreview() {
    AntheiaplantmanagerTheme {
        val addPlantViewModel = AddPlantViewModel(PlantRepositoryImplMock())
        PlantFormMedium(
            viewModel = addPlantViewModel,
            navigateBack = {}
        )
    }
}

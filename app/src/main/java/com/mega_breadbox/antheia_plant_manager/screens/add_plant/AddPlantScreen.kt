package com.mega_breadbox.antheia_plant_manager.screens.add_plant

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.model.data.mock.PlantRepositoryImplMock
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.mock.AccountServiceMock
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.mock.CloudServiceMock
import com.mega_breadbox.antheia_plant_manager.model.worker.mock.ReminderRepositoryImplMock
import com.mega_breadbox.antheia_plant_manager.screens.add_plant.util.AddPlantConstant
import com.mega_breadbox.antheia_plant_manager.util.ComposeText
import com.mega_breadbox.antheia_plant_manager.util.ReminderFrequency
import com.mega_breadbox.antheia_plant_manager.util.getReminderFrequency
import com.mega_breadbox.antheia_plant_manager.util.outlinedDisabledColorToActive
import com.mega_breadbox.antheia_plant_manager.util.outlinedTextFieldClickModifier
import com.mega_breadbox.compose.AntheiaplantmanagerTheme

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
        WindowWidthSizeClass.Expanded -> {
            PlantFormMedium(navigateBack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantFormCompact(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddPlantViewModel = hiltViewModel<AddPlantViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val locationSuggestions by viewModel.suggestions.collectAsStateWithLifecycle()
    val validatePlant by viewModel.validatePlant.collectAsStateWithLifecycle()
    val scroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    var selectedSuggestion by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { viewModel.savePlant(navigateBack) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(id = R.dimen.large_padding),
                vertical = dimensionResource(id = R.dimen.huge_padding)
            )
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

        ExposedDropdownMenuBox(
            expanded = locationSuggestions.isNotEmpty() && !selectedSuggestion,
            onExpandedChange = { },
            modifier = modifier
        ) {
            OutlinedTextField(
                value = viewModel.locationName,
                onValueChange = {
                    if(selectedSuggestion) selectedSuggestion = false
                    viewModel.updateLocation(it)
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
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = locationSuggestions.isNotEmpty() && !selectedSuggestion,
                onDismissRequest = { selectedSuggestion = true },
                modifier = modifier
            ) {
                LocationSuggestions(
                    locationSuggestions = locationSuggestions,
                    currentLocation = viewModel.locationName,
                    onEntryClick = {
                        selectedSuggestion = true
                        viewModel.updateLocation(it)
                    }
                )
            }
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = stringResource(R.string.watering_frequency),
                modifier = modifier.weight(1f)
            )
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            OutlinedTextField(
                value = viewModel.reminderFrequency(ReminderFrequency.WATERREMINDER),
                onValueChange = {},
                enabled = false,
                readOnly = true,
                colors = outlinedDisabledColorToActive(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.water_frequency_dropdown)
                    )
                },
                modifier = outlinedTextFieldClickModifier(onClick = { viewModel.updateWaterFrequencyDialog() })
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
            Text(
                text = stringResource(R.string.advanced_settings),
                color = MaterialTheme.colorScheme.onBackground
            )
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
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier
                            .weight(1f)
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.REPOTTINGREMINDER),
                        onValueChange = {},
                        enabled = false,
                        readOnly = true,
                        colors = outlinedDisabledColorToActive(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.repotting_frequency_dropdown)
                            )
                        },
                        modifier = outlinedTextFieldClickModifier(onClick = { viewModel.updateRepottingFrequencyDialog() })
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
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier.weight(1f)
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.FERTILIZERREMINDER),
                        onValueChange = {},
                        enabled = false,
                        colors = outlinedDisabledColorToActive(),
                        readOnly = true,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.fertilizing_frequency_dropdown)
                            )
                        },
                        modifier = outlinedTextFieldClickModifier(onClick = { viewModel.updateFertilizerFrequencyDialog() })
                    )
                }
                Spacer(
                    modifier = modifier
                        .weight(1f)
                )
            }
        }
        Button(
            enabled = validatePlant,
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    viewModel.savePlant(navigateBack)
                }
            },
            modifier = modifier
                .width(dimensionResource(id = R.dimen.button_wide))
                .padding(top = dimensionResource(id = R.dimen.large_padding))
        ) {
            Text(stringResource(R.string.save))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantFormMedium(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddPlantViewModel = hiltViewModel<AddPlantViewModel>(),
) {
    val locationSuggestions by viewModel.suggestions.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val validatePlant by viewModel.validatePlant.collectAsStateWithLifecycle()
    val scroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    var selectedSuggestion by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { viewModel.savePlant(navigateBack) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(id = R.dimen.large_padding),
                vertical = dimensionResource(id = R.dimen.huge_padding)
            )
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
        ExposedDropdownMenuBox(
            expanded = locationSuggestions.isNotEmpty() && !selectedSuggestion,
            onExpandedChange = { },
            modifier = modifier
        ) {
            OutlinedTextField(
                value = viewModel.locationName,
                onValueChange = {
                    if(selectedSuggestion) selectedSuggestion = false
                    viewModel.updateLocation(it)
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
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = locationSuggestions.isNotEmpty() && !selectedSuggestion,
                onDismissRequest = { selectedSuggestion = true }
            ) {
                LocationSuggestions(
                    locationSuggestions = locationSuggestions,
                    currentLocation = viewModel.locationName,
                    onEntryClick = {
                        selectedSuggestion = true
                        viewModel.updateLocation(it)
                    }
                )
            }
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.watering_frequency),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
            )
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            OutlinedTextField(
                value = viewModel.reminderFrequency(ReminderFrequency.WATERREMINDER),
                onValueChange = {},
                enabled = false,
                readOnly = true,
                colors = outlinedDisabledColorToActive(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.water_frequency_dropdown)
                    )
                },
                modifier = outlinedTextFieldClickModifier(onClick = { viewModel.updateWaterFrequencyDialog() })
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
            Text(
                text = stringResource(R.string.advanced_settings),
                color = MaterialTheme.colorScheme.onBackground,
            )
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
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.REPOTTINGREMINDER),
                        onValueChange = {},
                        enabled = false,
                        readOnly = true,
                        colors = outlinedDisabledColorToActive(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.repotting_frequency_dropdown)
                            )
                        },
                        modifier = outlinedTextFieldClickModifier(onClick = { viewModel.updateRepottingFrequencyDialog() })
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
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                    OutlinedTextField(
                        value = viewModel.reminderFrequency(ReminderFrequency.FERTILIZERREMINDER),
                        onValueChange = {},
                        enabled = false,
                        colors = outlinedDisabledColorToActive(),
                        readOnly = true,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.fertilizing_frequency_dropdown)
                            )
                        },
                        modifier = outlinedTextFieldClickModifier(onClick = { viewModel.updateFertilizerFrequencyDialog() })
                    )
                }
                Spacer(
                    modifier = modifier
                        .weight(1f)
                )
            }
        }
        Button(
            enabled = validatePlant,
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    viewModel.savePlant(navigateBack)
                }
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
                Text(
                    text = "Repeat how often?",
                    style = MaterialTheme.typography.displayLarge,
                )
                inputMap.forEach {
                    val reminderFrequencyAndDate = "${ it.key.asString()}+${ it.value }"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .padding(dimensionResource(id = R.dimen.dialog_padding))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape)))
                            .selectable(
                                selected = reminderFrequencyAndDate.getReminderFrequency() == selectedReminder.getReminderFrequency(),
                                onClick = { onRadioClick(reminderFrequencyAndDate) },
                            )
                    ) {
                        RadioButton(
                            selected = reminderFrequencyAndDate.getReminderFrequency() == selectedReminder.getReminderFrequency(),
                            onClick = { onRadioClick(reminderFrequencyAndDate) })
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(end = dimensionResource(id = R.dimen.dialog_padding))
                        ) {
                            Text(text = it.key.asString())
                            Text(
                                text = it.value,
                            )
                        }
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline,
                        modifier = modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.dialog_divider_padding))
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.dialog_padding),
                        bottom = dimensionResource(id = R.dimen.dialog_padding),
                        end = dimensionResource(id = R.dimen.dialog_confirm_padding)
                    )
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
fun LocationSuggestions(
    locationSuggestions: List<String>,
    onEntryClick: (String) -> Unit,
    currentLocation: String,
    modifier: Modifier = Modifier
) {
    locationSuggestions.forEach {
        if(it != currentLocation) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                onClick = { onEntryClick(it) },
                modifier = modifier
                    .width(dimensionResource(id = R.dimen.textfield_size_compact))
            )
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
        val addPlantViewModel = AddPlantViewModel(
            plantDatabase = PlantRepositoryImplMock(),
            accountService = AccountServiceMock(),
            reminderWorker = ReminderRepositoryImplMock(),
            cloudService = CloudServiceMock()

        )
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
        val addPlantViewModel = AddPlantViewModel(
            plantDatabase = PlantRepositoryImplMock(),
            accountService = AccountServiceMock(),
            reminderWorker = ReminderRepositoryImplMock(),
            cloudService = CloudServiceMock()
        )
        PlantFormMedium(
            viewModel = addPlantViewModel,
            navigateBack = {}
        )
    }
}

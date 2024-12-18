package com.example.antheia_plant_manager.screens.plant_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.plant_details.tabs.PlantDetailsNotes
import com.example.antheia_plant_manager.screens.plant_details.tabs.PlantDetailsSummary
import com.example.antheia_plant_manager.screens.plant_details.tabs.PlantDetailsSettings
import com.example.antheia_plant_manager.screens.plant_details.util.TabList
import com.example.antheia_plant_manager.screens.plant_details.util.getTaskButtonColor
import com.example.antheia_plant_manager.util.Header

@Composable
fun PlantDetailsScreen(
    navigateBack: () -> Unit,
) {
    PlantDetailsCompact(
        navigateBack = navigateBack,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlantDetailsCompact(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlantDetailsViewModel = hiltViewModel(),
) {
    val plantInfo by viewModel.plant.collectAsStateWithLifecycle()
    val plantAlerts by viewModel.plantAlert.collectAsStateWithLifecycle()
    val plantHasBeenUpdated by viewModel.plantHasBeenUpdated.collectAsStateWithLifecycle()
    val plantEntry by viewModel.plantEntry.collectAsStateWithLifecycle()
    val locationSuggestions by viewModel.suggestions.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val summaryScrollState = rememberScrollState()
    val screenSize = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
        ) {
            AnimatedVisibility(visible = !WindowInsets.isImeVisible && screenSize == WindowWidthSizeClass.COMPACT) {
                Column() {
                    Header(screenTitle = plantInfo.name)
                    Spacer(modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.medium_padding)))
                    PlantAvatar(
                        avatarSizeHorizontal = dimensionResource(id = R.dimen.plant_avatar_size),
                        avatarSizeVertical = dimensionResource(id = R.dimen.plant_avatar_size)
                    )
                    Spacer(modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.small_padding)))
                    PlantTabBar(selectedTabIndex = uiState.selectedTab) {
                        viewModel.updateSelectedTab(it)
                    }
                }
            }
            AnimatedVisibility(visible = !WindowInsets.isImeVisible && screenSize != WindowWidthSizeClass.COMPACT) {
                Column() {
                    Header(screenTitle = plantInfo.name)
                    Spacer(modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.medium_padding)))
                    PlantTabBar(selectedTabIndex = uiState.selectedTab) {
                        viewModel.updateSelectedTab(it)
                    }
                }
            }
            when (uiState.selectedTab) {
                TabList.SUMMARY.ordinal -> PlantDetailsSummary(
                    plantInfo = plantInfo,
                    plantAlerts = plantAlerts,
                    scrollState = summaryScrollState,
                    onTaskButtonClicked = { viewModel.taskButtonClicked(it) }
                )

                TabList.UPDATE.ordinal -> PlantDetailsSettings(
                    selectedReminder = uiState.currentSelectedReminder,
                    inputMap = uiState.dateMap,
                    currentPlant = plantEntry,
                    locationSuggestions = locationSuggestions,
                    updateName = { viewModel.updateName(it) },
                    updateLocation = { viewModel.updateLocation(it) },
                    validatePlant = plantHasBeenUpdated,
                    onDropdownClick = { viewModel.updateSelectedReminder(it) },
                    onRadioClick = { reminderString, reminderType ->
                        viewModel.updateTempReminderOfPlant(reminderString, reminderType)
                                   },
                    onConfirmClick = { reminderString, reminderType ->
                        viewModel.updateReminderOfPlant(reminderString, reminderType)
                    },
                    onDismissClick = { viewModel.clearSelectedReminder() },
                    onSaveClick = { viewModel.savePlant() },
                    onDeleteClick = { viewModel.deletePlant(navigateBack) }
                )
                TabList.NOTES.ordinal -> PlantDetailsNotes(
                    notesText = plantEntry.notes,
                    onNotesChange = { viewModel.updateNotesText(it) }
                )
            }
        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .alpha(uiState.buttonEffectAlpha)
                .background(getTaskButtonColor(uiState.latestButtonClicked))
        )
    }
}

@Composable
fun PlantAvatar(
    avatarSizeHorizontal: Dp,
    avatarSizeVertical: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.plant_avatar),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = modifier
                .size(
                    avatarSizeHorizontal,
                    avatarSizeVertical
                )
                .clip(CircleShape)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantTabBar(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
        TabList.entries.forEachIndexed { index, tabList ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) }
            ) {
                Text(
                    text = stringResource(id = tabList.title)
                )
            }
        }
    }
}



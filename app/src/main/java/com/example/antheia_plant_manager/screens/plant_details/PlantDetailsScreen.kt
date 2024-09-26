package com.example.antheia_plant_manager.screens.plant_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.plant_details.tabs.PlantDetailsNotes
import com.example.antheia_plant_manager.screens.plant_details.tabs.PlantDetailsSummary
import com.example.antheia_plant_manager.screens.plant_details.tabs.PlantDetailsSettings
import com.example.antheia_plant_manager.screens.plant_details.util.TabList
import com.example.antheia_plant_manager.util.Header

@Composable
fun PlantDetailsScreen() {
    PlantDetailsCompact()
}

@Composable
fun PlantDetailsCompact(
    viewModel: PlantDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val plantInfo by viewModel.plant.collectAsStateWithLifecycle()
    val plantAlerts by viewModel.plantAlert.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val summaryScrollState = rememberScrollState()
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
        when(uiState.selectedTab) {
            TabList.SUMMARY.ordinal -> PlantDetailsSummary(
                plantInfo = plantInfo,
                plantAlerts = plantAlerts,
                scrollState = summaryScrollState
            )
            TabList.UPDATE.ordinal -> PlantDetailsSettings()
            TabList.NOTES.ordinal -> PlantDetailsNotes()
        }
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



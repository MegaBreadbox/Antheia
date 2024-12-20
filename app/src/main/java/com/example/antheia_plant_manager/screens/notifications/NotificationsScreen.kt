package com.example.antheia_plant_manager.screens.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.plant_list.PlantListCompact

@Composable
fun NotificationsScreen(
    navigatePlantDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val plantAlerts by viewModel.plantAlerts.collectAsStateWithLifecycle()
    PlantListCompact(
        navigatePlantDetails = navigatePlantDetails,
        plantAlerts = plantAlerts,
        header = stringResource(id = R.string.notifications_header)
    )

}
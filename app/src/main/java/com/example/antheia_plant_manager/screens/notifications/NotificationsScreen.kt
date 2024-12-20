package com.example.antheia_plant_manager.screens.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.plant_list.PlantListCompact
import com.example.antheia_plant_manager.util.Header

@Composable
fun NotificationsScreen(
    navigatePlantDetails: (String) -> Unit,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val plantAlerts by viewModel.plantAlerts.collectAsStateWithLifecycle()
    PlantListCompact(
        navigatePlantDetails = navigatePlantDetails,
        plantAlerts = plantAlerts,
        header = stringResource(id = R.string.notifications_header),
        onEmptyList = {
            EmptyNotifications(header = stringResource(id = R.string.notifications_header))
        }

    )


}

@Composable
fun EmptyNotifications(
    header: String,
    modifier: Modifier = Modifier
) {
    Header(screenTitle = header)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.you_have_no_notifications),
            textAlign = TextAlign.Center,
            modifier = modifier
                .alpha(0.5F)
        )
    }

}


package com.example.antheia_plant_manager.screens.plant_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.util.Header
import com.example.antheia_plant_manager.util.PlantAlert
import com.example.antheia_plant_manager.util.Reminder
import com.example.antheia_plant_manager.util.cardColor

@Composable
fun PlantListScreen(
    navigatePlantDetails: (String) -> Unit,
    viewModel: PlantListViewModel = hiltViewModel()
) {
    val plantAlerts by viewModel.plantAlerts.collectAsStateWithLifecycle()
    PlantListCompact(
        navigatePlantDetails = navigatePlantDetails,
        plantAlerts = plantAlerts,
        header = viewModel.location
    )
}


@Composable
fun PlantListCompact(
    navigatePlantDetails: (String) -> Unit,
    plantAlerts: List<PlantAlert>,
    header: String,
    modifier: Modifier = Modifier,
) {
    val listModifier = Modifier
        .padding(
            horizontal = dimensionResource(id = R.dimen.large_padding),
            vertical = dimensionResource(id = R.dimen.medium_padding)
        )
    LazyColumn() {
        item {
            Header(screenTitle = header)
            Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        }

        itemsIndexed(plantAlerts, key = { _, plantAlert -> plantAlert.plantId  }) { index, plantAlert  ->
            PlantListEntry(
                index = index,
                plantAlert = plantAlert,
                navigatePlantDetails = navigatePlantDetails,
                plantListModifier = listModifier
            )
            HorizontalDivider(listModifier)
        }
    }
}

@Composable
fun PlantListEntry(
    index: Int,
    plantAlert: PlantAlert,
    navigatePlantDetails: (String) -> Unit,
    plantListModifier: Modifier,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = cardColor(index),
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.large_padding))
            .clip(CardDefaults.shape)
            .clickable { navigatePlantDetails(plantAlert.plantId) }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = plantListModifier
        ) {
            Text(
                text = plantAlert.plantName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.weight(1f),
            )
            PlantReminderIcon(
                taskReminder = plantAlert.waterAlert,
                iconVector = painterResource(id = R.drawable.water_drop_24dp_e8eaed_fill1_wght400_grad0_opsz24),
                contentDescriptionNoAlert = stringResource(R.string.no_water_needed),
                contentDescriptionAlert = stringResource(R.string.water_needed),
            )
            Spacer(modifier = modifier.padding(end = dimensionResource(id = R.dimen.medium_padding)))
            PlantReminderIcon(
                taskReminder = plantAlert.repottingAlert,
                iconVector = painterResource(id = R.drawable.potted_plant_24dp_e8eaed_fill1_wght400_grad0_opsz24),
                contentDescriptionNoAlert = stringResource(R.string.no_repotting_needed),
                contentDescriptionAlert = stringResource(R.string.repotting_needed)
            )
            Spacer(modifier = modifier.padding(end = dimensionResource(id = R.dimen.medium_padding)))
            PlantReminderIcon(
                taskReminder = plantAlert.fertilizerAlert,
                iconVector = painterResource(id = R.drawable.local_florist_24dp_e8eaed_fill1_wght400_grad0_opsz24),
                contentDescriptionNoAlert = stringResource(R.string.no_fertilizer_needed),
                contentDescriptionAlert = stringResource(R.string.fertilizer_needed)
            )
            Spacer(modifier = modifier.padding(end = dimensionResource(id = R.dimen.medium_padding)))
        }
    }

}

@Composable
fun PlantReminderIcon(
    taskReminder: Reminder,
    iconVector: Painter,
    contentDescriptionNoAlert: String,
    contentDescriptionAlert: String,
    modifier: Modifier = Modifier
) {
    when(taskReminder){
        Reminder.BeforeReminder -> Icon(
            painter = iconVector,
            contentDescription = contentDescriptionNoAlert,
            tint = MaterialTheme.colorScheme.tertiary
        )
        Reminder.DuringReminder -> Icon(
            painter = iconVector,
            contentDescription = contentDescriptionAlert,
            tint = MaterialTheme.colorScheme.primary
        )
        Reminder.AfterReminder -> Icon(
            painter = iconVector,
            contentDescription = contentDescriptionAlert,
            tint = MaterialTheme.colorScheme.error
        )
        Reminder.NotEnabled -> Icon(
            painter = iconVector,
            contentDescription = stringResource(R.string.no_reminder_set),
            modifier = modifier.alpha(0.2f)
        )
    }


}

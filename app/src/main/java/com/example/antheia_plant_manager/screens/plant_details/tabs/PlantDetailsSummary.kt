package com.example.antheia_plant_manager.screens.plant_details.tabs

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.screens.plant_details.util.ButtonType
import com.example.antheia_plant_manager.screens.plant_details.util.DISABLED_ALPHA
import com.example.antheia_plant_manager.screens.plant_details.util.fertilizeButtonColors
import com.example.antheia_plant_manager.screens.plant_details.util.repotButtonColors
import com.example.antheia_plant_manager.screens.plant_details.util.waterButtonColors
import com.example.antheia_plant_manager.util.PlantAlert
import com.example.antheia_plant_manager.util.Reminder
import com.example.antheia_plant_manager.util.daysSinceLastReminder
import com.example.antheia_plant_manager.util.getLastReminderDate
import com.example.compose.extendedColorScheme


@Composable
fun PlantDetailsSummary(
    plantInfo: Plant,
    plantAlerts: PlantAlert,
    scrollState: ScrollState,
    onTaskButtonClicked: (ButtonType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.big_padding)))
        TaskButton(
            onClick = { onTaskButtonClicked(ButtonType.WATER) },
            buttonText = stringResource(R.string.water),
            buttonColors = ButtonDefaults.waterButtonColors(),
            borderColor = MaterialTheme.extendedColorScheme().water.onColorContainer
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            TaskButton(
                onClick = { onTaskButtonClicked(ButtonType.REPOT) },
                buttonText = stringResource(R.string.repot),
                buttonColors = ButtonDefaults.repotButtonColors(),
                borderColor = MaterialTheme.extendedColorScheme().soil.onColorContainer
            )
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.large_padding)))
            TaskButton(
                onClick = { onTaskButtonClicked(ButtonType.FERTILIZE) },
                buttonText = stringResource(R.string.fertilize),
                buttonColors = ButtonDefaults.fertilizeButtonColors(),
                borderColor = MaterialTheme.extendedColorScheme().fertilizer.onColorContainer
            )
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
        PlantDetailEntry(
            entryText = stringResource(R.string.next_watering_date),
            plantAlert = plantAlerts.waterAlert,
            plantAlertDate = plantInfo.waterReminder.getLastReminderDate(),

            )
        PlantDetailEntry(
            entryText = stringResource(R.string.next_repotting_date),
            plantAlert = plantAlerts.repottingAlert,
            plantAlertDate = plantInfo.repottingReminder.getLastReminderDate()
        )
        PlantDetailEntry(
            entryText = stringResource(R.string.next_fertilizing_date),
            plantAlert = plantAlerts.fertilizerAlert,
            plantAlertDate = plantInfo.fertilizerReminder.getLastReminderDate()
        )
        PlantDetailEntry(
            entryText = stringResource(R.string.plant_name),
            data = plantInfo.name
        )
        PlantDetailEntry(
            entryText = stringResource(R.string.location),
            data = plantInfo.location
        )
        PlantDetailEntry(
            entryText = stringResource(R.string.date_added),
            data = plantInfo.dateAdded
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
    }
}

@Composable
fun TaskButton(
    onClick: () -> Unit,
    buttonText: String,
    buttonColors: ButtonColors,
    borderColor: Color
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = buttonColors,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.small_padding)),
        modifier = Modifier
            .defaultMinSize(
                minWidth = dimensionResource(id = R.dimen.task_button_size),
                minHeight = dimensionResource(id = R.dimen.task_button_size)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            )

    ) {
        Text(text = buttonText)
    }
}

@Composable
fun PlantDetailEntry(
    entryText: String,
    data: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(horizontal = dimensionResource(id = R.dimen.large_padding))
                .fillMaxWidth()
        ) {
            Text(
                text = entryText,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = data,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        HorizontalDivider(modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.medium_padding),
                vertical = dimensionResource(id = R.dimen.big_padding)
            )
        )
    }
}

@Composable
fun PlantDetailEntry(
    entryText: String,
    plantAlert: Reminder,
    plantAlertDate: String,
    modifier: Modifier = Modifier
){

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(horizontal = dimensionResource(id = R.dimen.large_padding))
                .fillMaxWidth()
        ) {
            Text(
                text = entryText,
                color = MaterialTheme.colorScheme.onBackground
            )
            when (plantAlert) {
                Reminder.AfterReminder -> Text(
                    text =
                    when(plantAlertDate.daysSinceLastReminder()) {
                        1 -> (stringResource(R.string.late_by_1_day))
                        in 2..<90  ->
                            stringResource(
                                R.string.late_by_days,
                                plantAlertDate.daysSinceLastReminder()
                            )
                        else -> stringResource(R.string.late_by_90_days)

                    },
                    color = MaterialTheme.colorScheme.error
                )

                Reminder.BeforeReminder -> Text(
                    text = plantAlertDate,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Reminder.DuringReminder -> Text(
                    text = stringResource(R.string.today),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Reminder.NotEnabled -> Text(
                    text = stringResource(R.string.no_reminder_set),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier
                        .alpha(DISABLED_ALPHA)
                )
            }
        }
        HorizontalDivider(modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.medium_padding),
                vertical = dimensionResource(id = R.dimen.big_padding)
            )
        )
    }

}

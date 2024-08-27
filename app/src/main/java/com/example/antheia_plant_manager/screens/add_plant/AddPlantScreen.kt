package com.example.antheia_plant_manager.screens.add_plant

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.antheia_plant_manager.R
import com.example.compose.AntheiaplantmanagerTheme

@Composable
fun AddPlantScreen() {
    PlantFormCompact()
}

@Composable
fun PlantFormCompact(
    modifier: Modifier = Modifier
) {
    var click: Boolean by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.large_padding))
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            placeholder = { Text(stringResource(R.string.plant_name)) }
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            placeholder = { Text(stringResource(R.string.location)) }
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.watering_frequency),
                modifier = modifier.weight(1f)
            )
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            OutlinedTextField(
                value = "Weekly",
                onValueChange = {},
                readOnly = true,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.water_frequency_dropdown)
                    )
                },
                modifier = modifier
                    .width(dimensionResource(id = R.dimen.textfield_size_mini))
                    .clickable {
                        click = true
                    }
            )
        }
        if(click) {
            Box() {
                frequencyDialog()
            }
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(R.string.advanced_settings))
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
            Switch(
                checked = false,
                onCheckedChange = { }
            )
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.small_padding)))
        HorizontalDivider()
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        if(true) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.repotting_frequency),
                    modifier = modifier.weight(1f)
                )
                Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                OutlinedTextField(
                    value = "Weekly",
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.repotting_frequency_dropdown)
                        )
                    },
                    modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_mini))
                )
            }
            Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.fertilizing_frequency),
                    modifier = modifier.weight(1f)
                )
                Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                OutlinedTextField(
                    value = "Weekly",
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.fertilizing_frequency_dropdown)
                        )
                    },
                    modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_mini))
                )
            }
            Spacer(modifier = modifier.weight(1f))
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .width(dimensionResource(id = R.dimen.button_wide))
        ) {
            Text("Save")
        }
    }
}

@Composable
fun frequencyDialog() {
    Dialog(onDismissRequest = { /*TODO*/ }) {
    }
}

@Composable
@Preview
fun AddPlantScreenPreview() {
    AntheiaplantmanagerTheme {
        AddPlantScreen()
    }
}
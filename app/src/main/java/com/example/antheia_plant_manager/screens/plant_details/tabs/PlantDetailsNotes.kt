package com.example.antheia_plant_manager.screens.plant_details.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.antheia_plant_manager.R

@Composable
fun PlantDetailsNotes(
    notesText: String,
    onNotesChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.large_padding))
            .imePadding()
    ) {
        BasicTextField(
            value = notesText,
            onValueChange = onNotesChange,
            textStyle = LocalTextStyle.current.merge(
                TextStyle(TextFieldDefaults.colors().focusedTextColor)
            ),
            cursorBrush = SolidColor(TextFieldDefaults.colors().cursorColor),

            modifier = modifier
                .fillMaxWidth()
        )
        if(notesText.isEmpty()) {
            Text(
                text = stringResource(R.string.enter_notes_here),
                style =  LocalTextStyle.current.merge(
                    TextStyle(TextFieldDefaults.colors().disabledPlaceholderColor)
                ),
            )
        }
    }
}

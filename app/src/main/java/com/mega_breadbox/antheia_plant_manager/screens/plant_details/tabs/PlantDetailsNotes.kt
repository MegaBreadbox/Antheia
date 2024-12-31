package com.mega_breadbox.antheia_plant_manager.screens.plant_details.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.mega_breadbox.antheia_plant_manager.R

@Composable
fun PlantDetailsNotes(
    notesText: String,
    onNotesChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.large_padding))
            .padding(
                top = dimensionResource(id = R.dimen.large_padding),
                bottom = dimensionResource(id = R.dimen.big_padding)
            )
            .imePadding()
    ) {
        BasicTextField(
            value = notesText,
            onValueChange = {
                onNotesChange(it)
            },
            textStyle = LocalTextStyle.current.merge(
                TextStyle(TextFieldDefaults.colors().focusedTextColor)
            ),
            cursorBrush = SolidColor(TextFieldDefaults.colors().cursorColor),
            decorationBox = { innerTextField -> Margins(innerTextField, notesText) },
            modifier = modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun Margins(
    innerTextField: @Composable () -> Unit,
    notesText: String,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints() {
        val maxHeight = this.maxHeight
        Box(
            modifier = modifier
                .height(maxHeight + dimensionResource(id = R.dimen.huge_padding))
                .imePadding()
        ) {
            if(notesText.isEmpty()) {
                Text(
                    text = stringResource(R.string.enter_notes_here),
                    style =  LocalTextStyle.current.merge(
                        TextStyle(TextFieldDefaults.colors().disabledPlaceholderColor)
                    ),
                )
            }
            innerTextField()
        }
    }

}

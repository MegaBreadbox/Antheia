package com.example.antheia_plant_manager.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

class ErrorText (
    @StringRes val signInErrorTextId: Int,
    vararg val arguments: Any,
) {
    @Composable
    fun asString(): String {
        return stringResource(signInErrorTextId, arguments)
    }
}
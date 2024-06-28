package com.example.antheia_plant_manager.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

data class ErrorText (
    val signInErrorTextId: Int,
    val arguments: Any? = null,
) {
    @Composable
    fun asString(signInErrorTextId: Int, arguments: Any): String {
        return stringResource(id = signInErrorTextId, arguments)
    }
}
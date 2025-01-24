package com.mega_breadbox.antheia_plant_manager.screens.account_settings.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mega_breadbox.antheia_plant_manager.R

enum class AccountDetail(@StringRes val stringId: Int) {
    USERNAME(R.string.username),
    EMAIL(R.string.email)
}

data class DialogState(
    @DrawableRes val iconResource: Int? = null,
    @StringRes val title: Int = 0,
    @StringRes val text: Int = 0,
    val dialogAction: () -> Unit = { },
    val isEnabled: Boolean = false,
)

package com.example.antheia_plant_manager.screens.account_settings.util

import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.util.ComposeText

sealed class AccountInfoType(val type: ComposeText) {
    data class Email(val email: String): AccountInfoType(ComposeText(R.string.email))
    data class Username(val username: String?): AccountInfoType(ComposeText(R.string.username))
}

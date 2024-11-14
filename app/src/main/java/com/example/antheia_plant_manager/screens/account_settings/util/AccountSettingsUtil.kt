package com.example.antheia_plant_manager.screens.account_settings.util

sealed interface AccountInfoType {
    data class Email(val email: String?): AccountInfoType
    data class Username(val username: String?): AccountInfoType
}

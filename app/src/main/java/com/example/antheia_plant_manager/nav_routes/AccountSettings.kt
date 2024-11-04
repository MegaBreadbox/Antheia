package com.example.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.nav_routes.util.AppScaffold
import com.example.antheia_plant_manager.screens.account_settings.AccountSettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object AccountSettings: NavigationObject

fun NavGraphBuilder.accountSettings(
    bottomAppBarNavigate: (NavigationObject) -> Unit
) {
    composable<AccountSettings>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        }
    ) {
        AppScaffold(
            currentDestination = AccountSettings,
            bottomAppBarNavigate = bottomAppBarNavigate
        ) {
            AccountSettingsScreen()
        }
    }
}
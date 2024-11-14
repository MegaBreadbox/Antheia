package com.example.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.nav_routes.util.AppScaffold
import com.example.antheia_plant_manager.screens.account_settings.AccountSettingsScreen
import com.example.antheia_plant_manager.screens.account_settings.util.AccountInfoType
import kotlinx.serialization.Serializable

@Serializable
object AccountSettings: NavigationObject

fun NavGraphBuilder.accountSettings(
    navigateChangeDetail: (AccountInfoType) -> Unit,
    navigateSignIn: () -> Unit,
    bottomAppBarNavigate: (NavigationObject) -> Unit
) {
    composable<AccountSettings>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        },
        deepLinks = listOf(
            navDeepLink { uriPattern = "antheia://password_reset" }
        )
    ) {
        AppScaffold(
            currentDestination = AccountSettings,
            bottomAppBarNavigate = bottomAppBarNavigate
        ) {
            AccountSettingsScreen(
                navigateChangeDetail = { },
                navigateSignIn = navigateSignIn
            )
        }
    }
}
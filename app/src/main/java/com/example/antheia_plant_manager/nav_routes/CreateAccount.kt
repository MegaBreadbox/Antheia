package com.example.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.screens.create_account.CreateAccountScreen
import kotlinx.serialization.Serializable

@Serializable
object CreateAccount: NavigationObject

fun NavGraphBuilder.createAccount(
    navigateHome: () -> Unit
) {
    composable<CreateAccount>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        }
    ) {
        CreateAccountScreen(
            navigateHome = navigateHome
        )
    }
}
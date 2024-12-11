package com.example.antheia_plant_manager.nav_routes

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.screens.sign_in.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Reauthenticate : NavigationObject

fun NavGraphBuilder.reauthenticate(
    navigateToSignIn: () -> Unit
) {
    composable<Reauthenticate>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        }
    ) {
        WelcomeScreen(
            navigateCreateAccount = { },
            navigateHome = navigateToSignIn,
            welcomeText = stringResource(R.string.sign_in_to_continue),
            isReauthenticate = true
        )
    }
}

package com.example.antheia_plant_manager.nav_routes

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.screens.sign_in.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Login

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable<Login>(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 2000,
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 100,
                )
            )
        }
    ) {
        WelcomeScreen(
            navigateCreateAccount = { navController.navigate(CreateAccount) }
        )
    }
}

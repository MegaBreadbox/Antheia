package com.example.antheia_plant_manager

import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.screens.sign_in.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object StartDestination

fun NavGraphBuilder.startDestination() {
    composable<StartDestination>(
        enterTransition = {
            fadeIn()
        }
    ) {
        WelcomeScreen()
    }
}
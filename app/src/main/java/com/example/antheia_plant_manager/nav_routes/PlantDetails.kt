package com.example.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.screens.plant_details.PlantDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlantDetails(val plantId: Int): NavigationObject

fun NavGraphBuilder.plantDetails() {
    composable<PlantDetails>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        },
    ) {
        PlantDetailsScreen()
    }
}

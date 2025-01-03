package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.plant_details.PlantDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlantDetails(val plantId: String): NavigationObject

fun NavGraphBuilder.plantDetails(
    navigateBack: () -> Unit
) {
    composable<PlantDetails>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        },
    ) {
        PlantDetailsScreen(
            navigateBack = navigateBack
        )
    }
}

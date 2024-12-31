package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.add_plant.AddPlantScreen
import kotlinx.serialization.Serializable

@Serializable
object AddPlant

fun NavGraphBuilder.addPlant(
    windowSize: WindowWidthSizeClass,
    navigateBack: () -> Unit
) {
    composable<AddPlant>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        }
    ) {
        AddPlantScreen(
            windowSize = windowSize,
            navigateBack = navigateBack
        )
    }
}
package com.example.antheia_plant_manager.nav_routes

import android.view.animation.Animation
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.screens.add_plant.AddPlantScreen
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
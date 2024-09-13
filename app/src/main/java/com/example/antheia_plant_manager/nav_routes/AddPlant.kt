package com.example.antheia_plant_manager.nav_routes

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.screens.add_plant.AddPlantScreen
import kotlinx.serialization.Serializable

@Serializable
object AddPlant

fun NavGraphBuilder.addPlant(
    windowSize: WindowWidthSizeClass,
    navigateBack: () -> Unit
) {
    composable<AddPlant>() {
        AddPlantScreen(
            windowSize = windowSize,
            navigateBack = navigateBack
        )
    }
}
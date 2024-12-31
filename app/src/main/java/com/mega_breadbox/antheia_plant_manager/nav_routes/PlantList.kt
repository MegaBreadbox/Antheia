package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.plant_list.PlantListScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlantList(val location: String): NavigationObject

fun NavGraphBuilder.plantList(
    windowSize: WindowWidthSizeClass,
    navigatePlantDetails: (String) -> Unit,
) {
    composable<PlantList>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        },
    ){

        PlantListScreen(
            navigatePlantDetails = navigatePlantDetails,
            windowSize = windowSize
        )
    }

}


package com.example.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.antheia_plant_manager.screens.plant_list.PlantListScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlantList(val location: String): NavigationObject

fun NavGraphBuilder.plantList(
    navigatePlantDetails: () -> Unit,
) {
    composable<PlantList> { backStackEntry ->
        val plantList: PlantList = backStackEntry.toRoute()
        PlantListScreen(
            location = plantList.location
        )
    }

}


package com.example.antheia_plant_manager.nav_routes

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.screens.home.HomeScreen
import com.example.antheia_plant_manager.util.AppBottomBar
import kotlinx.serialization.Serializable

@Serializable
object Home: NavigationObject

fun NavGraphBuilder.home(
    bottomAppBarNavigate: (NavigationObject) -> Unit,
) {
    composable<Home> {
        Scaffold(
            bottomBar = {
                AppBottomBar(
                    currentDestination = Home,
                    navigate = { bottomAppBarNavigate(it) }
                )
            }
        ) { padding ->
            HomeScreen(modifier = Modifier.padding(padding))
        }
    }
}
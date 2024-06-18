package com.example.antheia_plant_manager

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.antheia_plant_manager.screens.WelcomeScreen
import kotlinx.serialization.Serializable

@Composable
fun NavMenu(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = StartDestination,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<StartDestination>(
                enterTransition = {
                    fadeIn()
                }
            ){
                WelcomeScreen()
            }
        }
    }
}
@Serializable
object StartDestination
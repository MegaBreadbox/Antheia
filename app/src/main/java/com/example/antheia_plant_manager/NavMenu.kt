package com.example.antheia_plant_manager

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.antheia_plant_manager.nav_routes.Login
import com.example.antheia_plant_manager.nav_routes.createAccount
import com.example.antheia_plant_manager.nav_routes.loginScreen

@Composable
fun NavMenu(
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ) {
        loginScreen(navController)
        createAccount()
    }
}
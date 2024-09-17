package com.example.antheia_plant_manager

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.antheia_plant_manager.nav_routes.AddPlant
import com.example.antheia_plant_manager.nav_routes.CreateAccount
import com.example.antheia_plant_manager.nav_routes.Home
import com.example.antheia_plant_manager.nav_routes.Login
import com.example.antheia_plant_manager.nav_routes.NavigationObject
import com.example.antheia_plant_manager.nav_routes.addPlant
import com.example.antheia_plant_manager.nav_routes.createAccount
import com.example.antheia_plant_manager.nav_routes.home
import com.example.antheia_plant_manager.nav_routes.loginScreen
import com.example.antheia_plant_manager.nav_routes.notifications
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.serialization.Serializable

@Composable
fun NavMenu(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = checkLogin(),
        modifier = modifier
    ) {

        loginScreen(
            navigateCreateAccount = { navController.navigate(CreateAccount) },
            navigateHome = {
                navController.navigate(App) {
                    popUpToRoute
                }
            }
        )
        createAccount()
        navigation<App>(startDestination = Home) {
            home(
                bottomAppBarNavigate = { navController.navigate(it) },
                navigateAddPlant = { navController.navigate(AddPlant) }
            )
            notifications(bottomAppBarNavigate = { navController.navigate(it) })
            addPlant(
                windowSize = windowSize,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

private fun checkLogin(): NavigationObject {
    return if(Firebase.auth.currentUser == null) {
        Login
    } else {
        App
    }
}

@Serializable
object App: NavigationObject
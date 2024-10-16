package com.example.antheia_plant_manager

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
import com.example.antheia_plant_manager.nav_routes.PlantDetails
import com.example.antheia_plant_manager.nav_routes.PlantList
import com.example.antheia_plant_manager.nav_routes.addPlant
import com.example.antheia_plant_manager.nav_routes.createAccount
import com.example.antheia_plant_manager.nav_routes.home
import com.example.antheia_plant_manager.nav_routes.loginScreen
import com.example.antheia_plant_manager.nav_routes.notifications
import com.example.antheia_plant_manager.nav_routes.plantDetails
import com.example.antheia_plant_manager.nav_routes.plantList
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
                navigateAddPlant = { navController.navigate(AddPlant) },
                navigatePlantList = { navController.navigate(PlantList(it)) }
            )
            notifications(
                bottomAppBarNavigate = { navController.navigate(it) },
                navigatePlantDetails = { navController.navigate(PlantDetails(it)) }
            )
            addPlant(
                windowSize = windowSize,
                navigateBack = { navController.popBackStack() }
            )
            plantList(
                navigatePlantDetails = { navController.navigate(PlantDetails(it)) }
            )
            plantDetails(

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
package com.example.antheia_plant_manager

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.antheia_plant_manager.nav_routes.AccountSettings
import com.example.antheia_plant_manager.nav_routes.AccountSettingsEdit
import com.example.antheia_plant_manager.nav_routes.AddPlant
import com.example.antheia_plant_manager.nav_routes.CreateAccount
import com.example.antheia_plant_manager.nav_routes.Home
import com.example.antheia_plant_manager.nav_routes.LinkAccount
import com.example.antheia_plant_manager.nav_routes.Login
import com.example.antheia_plant_manager.nav_routes.NavigationObject
import com.example.antheia_plant_manager.nav_routes.Notifications
import com.example.antheia_plant_manager.nav_routes.PlantDetails
import com.example.antheia_plant_manager.nav_routes.PlantList
import com.example.antheia_plant_manager.nav_routes.Reauthenticate
import com.example.antheia_plant_manager.nav_routes.accountSettings
import com.example.antheia_plant_manager.nav_routes.accountSettingsEdit
import com.example.antheia_plant_manager.nav_routes.addPlant
import com.example.antheia_plant_manager.nav_routes.createAccount
import com.example.antheia_plant_manager.nav_routes.home
import com.example.antheia_plant_manager.nav_routes.linkAccount
import com.example.antheia_plant_manager.nav_routes.loginScreen
import com.example.antheia_plant_manager.nav_routes.notifications
import com.example.antheia_plant_manager.nav_routes.plantDetails
import com.example.antheia_plant_manager.nav_routes.plantList
import com.example.antheia_plant_manager.nav_routes.reauthenticate
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
            navigateCreateAccount = {
                navController.navigate(CreateAccount) {
                    launchSingleTop = true
                }
            },
            navigateHome = {
                navController.navigate(App) {
                    popUpTo<Login> {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
        createAccount(
            navigateHome = {
                navController.navigate(App) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
        navigation<App>(startDestination = Home) {
            home(
                bottomAppBarNavigate = {
                    navController.navigate(it) {
                        popUpTo<Home> {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateAddPlant = {
                    navController.navigate(AddPlant) {
                        launchSingleTop = true
                    }
                },
                navigatePlantList = {
                    navController.navigate(PlantList(it)) {
                        launchSingleTop = true
                    }
                }
            )
            notifications(
                bottomAppBarNavigate = {
                    navController.navigate(it) {
                        popUpTo<Notifications> {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigatePlantDetails = {
                    navController.navigate(PlantDetails(it)) {
                        launchSingleTop = true
                    }
                }
            )
            addPlant(
                windowSize = windowSize,
                navigateBack = {
                    navController.navigate(Home) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
            plantList(
                navigatePlantDetails = {
                    navController.navigate(PlantDetails(it)) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            plantDetails(
                navigateBack = {
                    navController.navigate(Home) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
            accountSettings(
                navigateChangeDetail = {
                    navController.navigate(AccountSettingsEdit(it)) {
                        launchSingleTop = true
                    }
                },
                navigateSignIn = {
                    navController.navigate(Login) {
                        popUpTo<AccountSettings> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                navigateReauthenticate = {
                    navController.navigate(Reauthenticate) {
                        launchSingleTop = true
                    }
                },
                navigateLinkAccount = {
                    navController.navigate(LinkAccount) {
                        launchSingleTop = true
                    }
                },
                bottomAppBarNavigate = {
                    navController.navigate(it) {
                        popUpTo<AccountSettings> {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            accountSettingsEdit(
                navigateBack = {
                    navController.navigate(AccountSettings) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
            reauthenticate(
                navigateToSignIn = {
                    navController.navigate(Login) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
            linkAccount(
                navigateHome = {
                    navController.navigate(Home) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
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
package com.example.antheia_plant_manager.nav_routes

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.screens.sign_in.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Login: NavigationObject

fun NavGraphBuilder.loginScreen(
    navigateCreateAccount: () -> Unit,
    navigateHome: () -> Unit
) {
    composable<Login>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        }
    ) {
        WelcomeScreen(
            navigateCreateAccount = navigateCreateAccount,
            navigateHome = navigateHome
        )
    }
}

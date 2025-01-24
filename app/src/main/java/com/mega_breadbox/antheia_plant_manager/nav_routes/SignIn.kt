package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.sign_in.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Login: NavigationObject

fun NavGraphBuilder.loginScreen(
    navigateCreateAccount: () -> Unit,
    navigateHome: () -> Unit,
    navigateAccountEdit: () -> Unit
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
            navigateHome = navigateHome,
            navigateAccountEdit = navigateAccountEdit
        )
    }
}

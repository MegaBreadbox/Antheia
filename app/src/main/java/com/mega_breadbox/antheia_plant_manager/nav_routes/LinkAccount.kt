package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.create_account.CreateAccountScreen
import kotlinx.serialization.Serializable

@Serializable
object LinkAccount: NavigationObject

fun NavGraphBuilder.linkAccount(
    navigateHome: () -> Unit
) {
    composable<LinkAccount>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        }
        ) {
        CreateAccountScreen(
            navigateHome = navigateHome,
            isLinkAccount = true
        )
    }
}

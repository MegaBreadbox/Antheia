package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.sign_in.WelcomeScreen
import com.mega_breadbox.antheia_plant_manager.screens.sign_in.util.ReauthenticateValue
import kotlinx.serialization.Serializable

@Serializable
data class Reauthenticate(val reauthenticateValue: ReauthenticateValue) : NavigationObject

fun NavGraphBuilder.reauthenticate(
    navigateToSignIn: () -> Unit,
    navigateAccountEdit: () -> Unit
) {
    composable<Reauthenticate>(
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        }
    ) {
        WelcomeScreen(
            navigateCreateAccount = { },
            navigateHome = navigateToSignIn,
            navigateAccountEdit = navigateAccountEdit,
            welcomeText = stringResource(R.string.sign_in_to_continue),
        )
    }
}

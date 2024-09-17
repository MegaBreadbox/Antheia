package com.example.antheia_plant_manager.nav_routes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.example.antheia_plant_manager.screens.notifications.NotificationsScreen
import com.example.antheia_plant_manager.nav_routes.util.AppScaffold
import kotlinx.serialization.Serializable

@Serializable
object Notifications: NavigationObject

fun NavGraphBuilder.notifications(
    bottomAppBarNavigate: (NavigationObject) -> Unit
) {
    composable<Notifications>(
        enterTransition = { AnimationConstants.fadeInAnimation() },
        exitTransition = { AnimationConstants.fadeOutAnimation() }
    ) {
        AppScaffold(
            currentDestination = Notifications,
            bottomAppBarNavigate = bottomAppBarNavigate,
        ) {
            NotificationsScreen()
        }
    }
}
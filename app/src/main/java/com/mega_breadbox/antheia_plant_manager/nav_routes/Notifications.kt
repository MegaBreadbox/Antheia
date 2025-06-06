package com.mega_breadbox.antheia_plant_manager.nav_routes
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.notifications.NotificationsScreen
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AppScaffold
import kotlinx.serialization.Serializable

@Serializable
object Notifications: NavigationObject

fun NavGraphBuilder.notifications(
    windowSize: WindowWidthSizeClass,
    navigatePlantDetails: (String) -> Unit,
    bottomAppBarNavigate: (NavigationObject) -> Unit
) {
    composable<Notifications>(
        enterTransition = { AnimationConstants.fadeInAnimation() },
        exitTransition = { AnimationConstants.fadeOutAnimation() },
        deepLinks = listOf(
            navDeepLink { uriPattern = "antheia://notifications" }
        )
    ) {
        AppScaffold(
            currentDestination = Notifications,
            bottomAppBarNavigate = bottomAppBarNavigate,
        ) {
            NotificationsScreen(
                windowSize = windowSize,
                navigatePlantDetails = navigatePlantDetails
            )
        }
    }
}
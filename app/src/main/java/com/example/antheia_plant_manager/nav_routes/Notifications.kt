package com.example.antheia_plant_manager.nav_routes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.screens.notifications.NotificationsScreen
import com.example.antheia_plant_manager.util.AppBottomBar
import com.example.antheia_plant_manager.util.AppScaffold
import kotlinx.serialization.Serializable

@Serializable
object Notifications: NavigationObject

fun NavGraphBuilder.notifications(
    bottomAppBarNavigate: (NavigationObject) -> Unit
) {
    composable<Notifications>() {
        AppScaffold(
            currentDestination = Notifications,
            bottomAppBarNavigate = bottomAppBarNavigate,
        ) {
            NotificationsScreen()
        }
    }
}
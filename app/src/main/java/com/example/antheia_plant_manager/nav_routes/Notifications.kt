package com.example.antheia_plant_manager.nav_routes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.antheia_plant_manager.screens.notifications.NotificationsScreen
import com.example.antheia_plant_manager.util.AppBottomBar
import kotlinx.serialization.Serializable

@Serializable
object Notifications: NavigationObject

fun NavGraphBuilder.notifications(
    bottomAppBarNavigate: (NavigationObject) -> Unit
) {
    composable<Notifications>() {
        Scaffold(
            bottomBar = {
                AppBottomBar(
                    currentDestination = Notifications,
                    navigate = { bottomAppBarNavigate(it) }
                )
            }
        ) { padding ->
            NotificationsScreen(modifier = Modifier.padding(padding))

        }
    }
}
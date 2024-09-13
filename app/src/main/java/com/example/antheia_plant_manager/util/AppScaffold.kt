package com.example.antheia_plant_manager.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.nav_routes.Home
import com.example.antheia_plant_manager.nav_routes.NavigationObject
import com.example.antheia_plant_manager.nav_routes.Notifications


@Composable
fun AppScaffold(
    currentDestination: NavigationObject,
    bottomAppBarNavigate: (NavigationObject) -> Unit,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentDestination = currentDestination,
                navigate = { bottomAppBarNavigate(it) }
            )
        },
        floatingActionButton = floatingActionButton,

    ) {
        Column(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
fun AppBottomBar(
    currentDestination: NavigationObject,
    navigate: (NavigationObject) -> Unit,
    modifier: Modifier = Modifier
) {
    val iconModifier = modifier
        .size(dimensionResource(id = R.dimen.icon_size))

    val iconTouchTarget = modifier
        .size(dimensionResource(id = R.dimen.icon_button_touch_target))

    BottomAppBar(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
        ) {
            BottomBarEntry.entries.forEach { entry ->
                TextButton(
                    onClick = {
                        navigate(entry.navRoute)
                    },
                    modifier = iconTouchTarget
                ) {
                    if (currentDestination == entry.navRoute) {
                        Icon(
                            imageVector = entry.iconVectorFilled,
                            contentDescription = stringResource(entry.contentDescription),
                            modifier = iconModifier
                        )
                    } else {
                        Icon(
                            imageVector = entry.iconVectorOutlined,
                            contentDescription = stringResource(entry.contentDescription),
                            modifier = iconModifier
                        )
                    }
                }
            }
        }

    }
}



enum class BottomBarEntry(
    val navRoute: NavigationObject,
    val iconVectorFilled: ImageVector,
    val iconVectorOutlined: ImageVector,
    val contentDescription: Int
) {
    NOTIFICATIONS(
        navRoute = Notifications,
        iconVectorFilled = Icons.Filled.Notifications,
        iconVectorOutlined = Icons.Outlined.Notifications,
        contentDescription = R.string.notifications
    ),
    HOME(
        navRoute = Home,
        iconVectorFilled = Icons.Filled.Home,
        iconVectorOutlined = Icons.Outlined.Home,
        contentDescription = R.string.home
    ),
}



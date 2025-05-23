package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.home.HomeScreen
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AppScaffold
import kotlinx.serialization.Serializable

@Serializable
object Home: NavigationObject

fun NavGraphBuilder.home(
    windowSize: WindowWidthSizeClass,
    navigateAddPlant: () -> Unit,
    navigatePlantList: (String) -> Unit,
    bottomAppBarNavigate: (NavigationObject) -> Unit,
) {
    composable<Home>(
        enterTransition = { AnimationConstants.fadeInAnimation() },
        exitTransition = { AnimationConstants.fadeOutAnimation() }
    ) {
        AppScaffold(
            currentDestination = Home,
            bottomAppBarNavigate = bottomAppBarNavigate,
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { navigateAddPlant() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = null
                        )
                        Spacer(Modifier.width(dimensionResource(id = R.dimen.medium_padding)))
                        Text(text = stringResource(R.string.add_plant))
                    }
                }
            }
        ) {
            HomeScreen(
                navigatePlantList = navigatePlantList,
                windowSize = windowSize
            )
        }
    }
}
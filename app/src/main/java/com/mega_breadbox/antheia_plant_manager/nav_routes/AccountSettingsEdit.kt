package com.mega_breadbox.antheia_plant_manager.nav_routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.mega_breadbox.antheia_plant_manager.nav_routes.util.AnimationConstants
import com.mega_breadbox.antheia_plant_manager.screens.account_settings.util.AccountDetail
import com.mega_breadbox.antheia_plant_manager.screens.account_settings_edit.AccountSettingsEditScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class AccountSettingsEdit(val accountDetail: AccountDetail): NavigationObject

fun NavGraphBuilder.accountSettingsEdit(
    navigateBack: () -> Unit,
) {
    composable<AccountSettingsEdit>(
        typeMap = mapOf(
            typeOf<AccountDetail>() to NavType.EnumType(AccountDetail::class.java)
        ),
        enterTransition = {
            AnimationConstants.fadeInAnimation()
        },
        exitTransition = {
            AnimationConstants.fadeOutAnimation()
        },
    ){
        AccountSettingsEditScreen(
            navigateBack = navigateBack
        )
    }

}
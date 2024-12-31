package com.mega_breadbox.antheia_plant_manager.nav_routes.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

object AnimationConstants {
    private const val fadeInDuration = 1000
    private const val fadeOutDuration = 100

    fun fadeInAnimation(): EnterTransition {
        return fadeIn(
            animationSpec = tween(
                fadeInDuration
            )
        )
    }

    fun fadeOutAnimation(): ExitTransition {
        return fadeOut(
            animationSpec = tween(
                fadeOutDuration
            )
        )
    }
}
package com.example.antheia_plant_manager.welcome_screen

import com.example.antheia_plant_manager.screens.WelcomeViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WelcomeViewModelTest {
    private val viewModel = WelcomeViewModel()

    @Test
    fun welcomeViewModel_updateWelcomeText_Success() {
        viewModel.updateWelcomeText('a')
        val welcomeText = viewModel.uiState.value.processWelcomeText
        assertEquals("a", welcomeText)
    }

}
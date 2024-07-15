package com.example.antheia_plant_manager.welcome_screen

import com.example.antheia_plant_manager.model.service.GoogleSignIn
import com.example.antheia_plant_manager.model.service.module.GoogleSignInModule
import com.example.antheia_plant_manager.screens.sign_in.WelcomeViewModel
import com.example.antheia_plant_manager.welcome_screen.mocks.AccountServiceMock
import com.example.antheia_plant_manager.welcome_screen.mocks.GoogleSignInMock
import dagger.Lazy
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase.assertEquals
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(GoogleSignInModule::class)
class WelcomeViewModelTest {

    @Inject
    lateinit var googleSignInMock: Lazy<GoogleSignIn>

    private val viewModel = WelcomeViewModel(
        AccountServiceMock(),
        googleSignInMock
    )

    @Test
    fun welcomeViewModel_updateWelcomeText_Success() {
        viewModel.updateWelcomeText('a')
        val welcomeText = viewModel.uiState.value.processWelcomeText
        assertEquals("a", welcomeText)
    }

}
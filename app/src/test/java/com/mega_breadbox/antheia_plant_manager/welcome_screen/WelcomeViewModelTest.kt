package com.mega_breadbox.antheia_plant_manager.welcome_screen

import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.GoogleSignIn
import screens.sign_in.WelcomeViewModel
import com.mega_breadbox.antheia_plant_manager.mocks.AccountServiceMock
import com.mega_breadbox.antheia_plant_manager.mocks.GoogleSignInMock
import com.mega_breadbox.antheia_plant_manager.test_rules.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class WelcomeViewModelTest {


    private val googleSignInMock: GoogleSignIn = GoogleSignInMock()

    private val viewModel = WelcomeViewModel(
        accountService = AccountServiceMock(),
        googleSignIn = TODO(),
        cloudService = TODO(),
        plantDatabase = TODO()
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun welcomeViewModel_updateWelcomeText_Success() {
        viewModel.updateWelcomeText('a')
        val welcomeText = viewModel.uiState.value.processWelcomeText
        assertEquals("a", welcomeText)
    }

    @Test
    fun welcomeViewModel_updateEmail_Success() {
        viewModel.updateEmail("a")
        assertEquals("a", viewModel.emailText)
    }

    @Test
    fun welcomeViewModel_updatePassword_Success() {
        viewModel.updatePassword("a")
        assertEquals("a", viewModel.passwordText)
    }

    @Test
    fun welcomeViewModel_updatePasswordIsVisible_Success() {
        viewModel.updateIsPasswordVisible()
        assertEquals(true, viewModel.uiState.value.isPasswordVisible)
    }

    @Test
    fun welcomeViewModel_signIn_Success() = runTest {
        viewModel.updateEmail("a@gmail.com")
        viewModel.updatePassword("123456")
        viewModel.signIn(isReauthenticate = false) { }
        assert(viewModel.uiState.value.errorText == null)
    }

    @Test
    fun welcomeViewModel_anonymousSignIn_Success() = runTest {
        viewModel.anonymousSignIn() { }
        assert(viewModel.uiState.value.errorText == null)
    }

    @Test
    fun welcomeViewModel_updateErrorText_Success() {
        viewModel.updateErrorText(12)
        assertEquals(12, viewModel.uiState.value.errorText?.textId)
    }



}
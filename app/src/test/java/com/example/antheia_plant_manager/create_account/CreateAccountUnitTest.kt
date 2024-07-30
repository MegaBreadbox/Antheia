package com.example.antheia_plant_manager.create_account

import com.example.antheia_plant_manager.screens.create_account.CreateAccountViewModel
import com.example.antheia_plant_manager.mocks.AccountServiceMock
import com.example.antheia_plant_manager.test_rules.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


class CreateAccountUnitTest {
    private val viewModel = CreateAccountViewModel(
        accountService = AccountServiceMock()
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun createAccountViewModel_UpdateWelcomeText_Success() {
        viewModel.changeWelcomeText('a')
        val welcomeText = viewModel.uiState.value.welcomeText
        assertEquals("a", welcomeText)
    }

    @Test
    fun createAccountViewModel_UpdateEmail_Success() {
        viewModel.updateEmailText("a")
        assertEquals("a", viewModel.emailText)
    }

    @Test
    fun createAccountViewModel_UpdatePassword_Success() {
        viewModel.updatePasswordText("a")
        assertEquals("a", viewModel.passwordText)
    }

    @Test
    fun createAccountViewModel_UpdateConfirmPassword_Success() {
        viewModel.updateConfirmPasswordText("a")
        assertEquals("a", viewModel.confirmPasswordText)
    }

    @Test
    fun createAccountViewModel_UpdatePasswordIsVisible_Success() {
        viewModel.updateIsPasswordVisible()
        assertEquals(true, viewModel.uiState.value.isPasswordVisible)
    }

    @Test
    fun createAccountViewModel_CreateAccount_Success() = runTest {
        viewModel.updateEmailText("a")
        viewModel.updatePasswordText("asdfgH@1")
        viewModel.updateConfirmPasswordText("asdfgH@1")
        viewModel.createAccount()
        assert(viewModel.uiState.value.errorText == null)
    }

    @Test
    fun createAccountViewModel_CreateAccount_PasswordDoesNotMatch_Error() = runTest {
        viewModel.updateEmailText("a")
        viewModel.updatePasswordText("asdfgH@1")
        viewModel.updateConfirmPasswordText("asdfgH@2")
        viewModel.createAccount()
        assert(viewModel.uiState.value.errorText != null)
    }

    @Test
    fun createAccountViewModel_CreateAccount_PasswordIsWeak_Error() = runTest {
        viewModel.updateEmailText("a")
        viewModel.updatePasswordText("asdfg")
        viewModel.updateConfirmPasswordText("asdfg")
        viewModel.createAccount()
        assert(viewModel.uiState.value.errorText != null)
    }

}
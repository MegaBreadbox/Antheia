package com.example.antheia_plant_manager.welcome_screen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.HiltComponentActivity
import com.example.antheia_plant_manager.screens.sign_in.WelcomeScreen
import com.example.compose.AntheiaplantmanagerTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class WelcomeInstrumentedTest {


    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    private lateinit var signInMessage: String
    private lateinit var emptyCredentialsMessage: String
    private lateinit var invalidCredentialsMessage: String
    private lateinit var signInError: String
    private lateinit var emailTextBox: String
    private lateinit var passwordTextBox: String
    private lateinit var anonymousSignInMessage: String
    private lateinit var googleSignInMessage: String
    private lateinit var googleSignInError: String

    @Before
    fun init() {
        hiltRule.inject()
        signInMessage = composeTestRule.activity.getString(R.string.sign_in)
        googleSignInMessage = composeTestRule.activity.getString(R.string.sign_in_with_google)
        anonymousSignInMessage = composeTestRule.activity.getString(R.string.continue_without_an_account)
        emptyCredentialsMessage = composeTestRule.activity.getString(R.string.empty_credentials_error)
        invalidCredentialsMessage = composeTestRule.activity.getString(R.string.wrong_user_details_error)
        signInError = composeTestRule.activity.getString(R.string.error_occurred_while_signing_in)
        googleSignInError = composeTestRule.activity.getString(R.string.google_sign_in_cancelled)
        emailTextBox = composeTestRule.activity.getString(R.string.email)
        passwordTextBox = composeTestRule.activity.getString(R.string.password)

        composeTestRule.setContent {
            AntheiaplantmanagerTheme {
                WelcomeScreen()
            }
        }
    }

    @Test
    fun welcomeViewModel_SignIn_NoInputError() {
        composeTestRule.onNodeWithText(signInMessage).performClick()
        composeTestRule.onNodeWithText(emptyCredentialsMessage).assertExists()
    }

    @Test
    fun welcomeViewModel_SignIn_InvalidCredentialsError() {
        composeTestRule.onNodeWithText(emailTextBox).performClick().performTextInput("a")
        composeTestRule.onNodeWithText(passwordTextBox).performClick().performTextInput("b")
        composeTestRule.onNodeWithText(signInMessage).performClick()
        composeTestRule.onNodeWithText(invalidCredentialsMessage).assertExists()
    }

    @Test
    fun welcomeViewModel_AnonymousSignIn_Success() {
        composeTestRule.onNodeWithText(anonymousSignInMessage).performClick()
        composeTestRule.onNodeWithText(signInError).assertDoesNotExist()
    }

    @Test
    fun welcomeViewModel_googleSignIn_Cancelled() {
        composeTestRule.onNodeWithContentDescription(googleSignInMessage).performClick()
        Espresso.pressBack()
        composeTestRule.onNodeWithText(googleSignInError).assertExists()
    }
}
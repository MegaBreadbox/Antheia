package com.example.antheia_plant_manager.welcome_screen

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.HiltComponentActivity
import com.example.antheia_plant_manager.screens.sign_in.WelcomeScreen
import com.example.antheia_plant_manager.screens.sign_in.WelcomeViewModel
import com.example.compose.AntheiaplantmanagerTheme
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.EntryPoint
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class WelcomeInstrumentedTest {


    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    private lateinit var signInMessage: String
    private lateinit var emptyCredentialsMessage: String
    private lateinit var emailTextBox: String
    private lateinit var passwordTextBox: String

    @Before
    fun init() {
        hiltRule.inject()
        signInMessage = composeTestRule.activity.getString(R.string.sign_in)
        emptyCredentialsMessage = composeTestRule.activity.getString(R.string.empty_credentials_error)
        emailTextBox = composeTestRule.activity.getString(R.string.email)
        passwordTextBox = composeTestRule.activity.getString(R.string.password)

    }

    @Test
    fun welcomeViewModel_SignIn_NoInputError() {
        composeTestRule.setContent {
            AntheiaplantmanagerTheme {
                WelcomeScreen()
            }
        }
        composeTestRule.onNodeWithText(signInMessage).performClick()
        composeTestRule.onNodeWithText(emptyCredentialsMessage).assertExists()
    }

}
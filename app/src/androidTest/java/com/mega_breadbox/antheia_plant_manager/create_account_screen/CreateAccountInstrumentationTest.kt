package com.mega_breadbox.antheia_plant_manager.create_account_screen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mega_breadbox.antheia_plant_manager.HiltComponentActivity
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.screens.create_account.CreateAccountScreen
import com.mega_breadbox.antheia_plant_manager.ui.theme.AntheiaplantmanagerTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CreateAccountInstrumentationTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    private lateinit var emailTextBox: String
    private lateinit var passwordTextBox: String
    private lateinit var confirmPasswordTextBox: String
    private lateinit var createAccountButton: String
    private lateinit var emptyFieldError: String
    private lateinit var emailFormatError: String

    @Before
    fun init() {
        hiltRule.inject()

        emailTextBox = composeTestRule.activity.getString(R.string.email)
        passwordTextBox = composeTestRule.activity.getString(R.string.password)
        confirmPasswordTextBox = composeTestRule.activity.getString(R.string.confirm_password)
        emptyFieldError = composeTestRule.activity.getString(R.string.create_account_empty_field_error)
        createAccountButton = composeTestRule.activity.getString(R.string.create_account)
        emailFormatError = composeTestRule.activity.getString(R.string.create_account_invalid_email_error)
        composeTestRule.setContent {
            AntheiaplantmanagerTheme {
                CreateAccountScreen(navigateHome = { })
            }
        }
    }

    @Test
    fun createAccountViewModel_CreateAccount_EmptyFieldError() {
        composeTestRule.onNodeWithText(emailTextBox).performClick().performTextInput("")
        composeTestRule.onNodeWithText(passwordTextBox).performClick().performTextInput("asdfgH1@")
        composeTestRule.onNodeWithText(confirmPasswordTextBox).performClick().performTextInput("asdfgH1@")
        composeTestRule.onNodeWithText(createAccountButton).performClick()
        composeTestRule.onNodeWithText(emptyFieldError).assertExists()
    }
}
package screens.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.util.WelcomeTextCompact

@Composable
fun WelcomeScreen(
    navigateCreateAccount: () -> Unit,
    navigateHome: () -> Unit,
    navigateAccountEdit: () -> Unit,
    welcomeText: String = "",
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel<WelcomeViewModel>(),
) {
    val welcomeTextState by viewModel.welcomeText.collectAsStateWithLifecycle()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        WelcomeTextCompact(
            processWelcomeText = welcomeTextState,
            updateWelcomeText = { viewModel.updateWelcomeText(it) },
            welcomeText = stringResource(R.string.welcome_to_the_antheia_demo)
        )
    }
}

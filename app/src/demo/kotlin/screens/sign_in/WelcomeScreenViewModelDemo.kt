package screens.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega_breadbox.antheia_plant_manager.util.SUBSCRIBE_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import data.EnableScreenRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val enableScreen: EnableScreenRepo
): ViewModel() {

    private val _welcomeText = MutableStateFlow("")
    val welcomeText = _welcomeText.asStateFlow()

    val enableScreenFlow = enableScreen.welcomeScreenEnableFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = true
        )


    fun updateWelcomeText(newText: Char) {
        _welcomeText.update{
            it + newText
        }
    }

    fun disableWelcomeScreen() {
        viewModelScope.launch {
            enableScreen.disableWelcomeScreen()
        }
    }
}
package screens.sign_in

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(): ViewModel() {

    private val _welcomeText = MutableStateFlow("")
    val welcomeText = _welcomeText.asStateFlow()

    fun updateWelcomeText(newText: Char) {
        _welcomeText.update{
            it + newText
        }
    }
}
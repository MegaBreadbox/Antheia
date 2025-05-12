package data

import kotlinx.coroutines.flow.Flow

interface EnableScreenRepo {
    val welcomeScreenEnableFlow: Flow<Boolean>

    suspend fun disableWelcomeScreen()
}
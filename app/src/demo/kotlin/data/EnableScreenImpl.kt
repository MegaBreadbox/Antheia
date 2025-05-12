package data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class EnableScreenImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
): EnableScreenRepo {

    //One key shared to all EnableScreenImpls
    private companion object {
        val IS_WELCOME_ENABLED = booleanPreferencesKey("is_welcome_enabled")
    }

    override val welcomeScreenEnableFlow: Flow<Boolean>
        get() = datastore.data
            .map { preferences ->
                preferences[IS_WELCOME_ENABLED] ?: true
            }
            .catch {
                if(it is IOException) {
                    emit(true)
                }
            }


    override suspend fun disableWelcomeScreen() {
        datastore.edit { preferences ->
            preferences[IS_WELCOME_ENABLED] = false
        }
    }

}
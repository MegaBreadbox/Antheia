package data.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DatastoreProvider {
    private const val SCREEN_ENABLE_PREFERENCES = "screen_enable_preferences"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = SCREEN_ENABLE_PREFERENCES
    )

    @Provides
    @Singleton
    fun provideDatastore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }


}
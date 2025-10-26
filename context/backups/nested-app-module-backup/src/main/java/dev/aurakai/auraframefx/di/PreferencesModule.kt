package dev.aurakai.auraframefx.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.data.UserPreferences
import javax.inject.Singleton

/**
 * Hilt Module for providing UserPreferences.
 * UserPreferences manages user-specific settings and configuration.
 */
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    /**
     * Provides singleton UserPreferences instance.
     * UserPreferences uses DataStore for persistent storage of user settings.
     *
     * @param context Application context
     * @param dataStore DataStore for preferences persistence
     * @return UserPreferences instance
     */
    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context,
        dataStore: DataStore<Preferences>
    ): UserPreferences = UserPreferences(context, dataStore)
}

package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.state.AppStateManager
import javax.inject.Singleton

/**
 * Hilt Module for providing application state management dependencies.
 * AppStateManager centralizes application-wide state using Kotlin Flows.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppStateModule {

    /**
     * Provides singleton AppStateManager instance.
     * AppStateManager manages global app state including initialization status,
     * current screen, network status, and error states.
     *
     * @return AppStateManager instance
     */
    @Provides
    @Singleton
    fun provideAppStateManager(): AppStateManager = AppStateManager()
}

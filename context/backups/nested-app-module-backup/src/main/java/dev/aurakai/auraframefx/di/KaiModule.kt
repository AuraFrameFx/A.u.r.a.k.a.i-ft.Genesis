package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.ui.KaiController
import javax.inject.Singleton

/**
 * Hilt Module for providing KaiController.
 * KaiController manages the Kai UI interactions and state.
 */
@Module
@InstallIn(SingletonComponent::class)
object KaiModule {

    /**
     * Provides singleton KaiController instance.
     * KaiController manages Kai-specific UI elements, gestures, and interactions.
     *
     * @return KaiController instance
     */
    @Provides
    @Singleton
    fun provideKaiController(): KaiController = KaiController()
}

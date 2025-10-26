package dev.aurakai.auraframefx.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module for providing WorkManager dependencies.
 * Configures WorkManager with Hilt support for injecting dependencies into Workers.
 */
@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    /**
     * Provides WorkManager Configuration with HiltWorkerFactory.
     * This enables Hilt dependency injection in Worker classes annotated with @HiltWorker.
     *
     * @param workerFactory HiltWorkerFactory for creating Workers with injected dependencies
     * @return Configured WorkManager Configuration instance
     */
    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(
        workerFactory: HiltWorkerFactory
    ): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    /**
     * Provides the singleton WorkManager instance.
     * WorkManager is initialized with the Hilt configuration automatically.
     *
     * @param context Application context
     * @return WorkManager instance
     */
    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context)
}

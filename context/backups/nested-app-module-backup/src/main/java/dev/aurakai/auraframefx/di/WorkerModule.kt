package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt Module for WorkManager Worker configuration.
 *
 * Note: HiltWorkerFactory is automatically provided by Hilt when you add the
 * androidx.hilt:hilt-work dependency. This module serves as documentation
 * that Workers can use @HiltWorker annotation for dependency injection.
 *
 * Usage in Workers:
 * ```
 * @HiltWorker
 * class MyWorker @AssistedInject constructor(
 *     @Assisted appContext: Context,
 *     @Assisted workerParams: WorkerParameters,
 *     private val repository: MyRepository
 * ) : Worker(appContext, workerParams) {
 *     override fun doWork(): Result {
 *         // Use injected repository
 *         return Result.success()
 *     }
 * }
 * ```
 */
@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {
    // HiltWorkerFactory is provided automatically by Hilt
    // No explicit @Provides needed - just ensure dependencies are available
}

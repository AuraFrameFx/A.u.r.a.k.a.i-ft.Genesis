// File: romtools/src/main/kotlin/dev/aurakai/auraframefx/romtools/di/RomToolsModule.kt
package dev.aurakai.auraframefx.romtools.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.romtools.BackupManager
import dev.aurakai.auraframefx.romtools.BackupManagerImpl
import dev.aurakai.auraframefx.romtools.FlashManager
import dev.aurakai.auraframefx.romtools.FlashManagerImpl
import dev.aurakai.auraframefx.romtools.RecoveryManager
import dev.aurakai.auraframefx.romtools.RecoveryManagerImpl
import dev.aurakai.auraframefx.romtools.RomVerificationManager
import dev.aurakai.auraframefx.romtools.RomVerificationManagerImpl
import dev.aurakai.auraframefx.romtools.SystemModificationManager
import dev.aurakai.auraframefx.romtools.SystemModificationManagerImpl
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManagerImpl
import javax.inject.Singleton

/**
 * ROM Tools Hilt module providing dependency injection bindings.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RomToolsModule {

    /**
     * Binds the concrete BootloaderManagerImpl as the implementation for BootloaderManager in the DI graph.
     *
     * @param bootloaderManagerImpl The concrete implementation to be provided whenever BootloaderManager is injected.
     * @return The BootloaderManager interface bound to the provided implementation.
     */
    @Binds
    @Singleton
    abstract fun bindBootloaderManager(
        bootloaderManagerImpl: BootloaderManagerImpl
    ): BootloaderManager

    /**
     * Binds RecoveryManagerImpl as the concrete implementation for RecoveryManager in the dependency-injection graph.
     *
     * @param recoveryManagerImpl The implementation instance to bind.
     * @return The bound RecoveryManager implementation.
     */
    @Binds
    @Singleton
    abstract fun bindRecoveryManager(
        recoveryManagerImpl: RecoveryManagerImpl
    ): RecoveryManager

    /**
     * Binds SystemModificationManagerImpl as the implementation of SystemModificationManager in the dependency injection graph.
     *
     * @param systemModificationManagerImpl The implementation instance to bind.
     * @return The bound SystemModificationManager implementation.
     */
    @Binds
    @Singleton
    abstract fun bindSystemModificationManager(
        systemModificationManagerImpl: SystemModificationManagerImpl
    ): SystemModificationManager

    /**
     * Binds the concrete FlashManagerImpl as the implementation to provide for `FlashManager`.
     *
     * @param flashManagerImpl The implementation instance to be used whenever `FlashManager` is injected.
     * @return The `FlashManager` binding.
     */
    @Binds
    @Singleton
    abstract fun bindFlashManager(
        flashManagerImpl: FlashManagerImpl
    ): FlashManager

    /**
     * Binds RomVerificationManagerImpl as the implementation to provide when RomVerificationManager is requested.
     *
     * @return The bound RomVerificationManager implementation.
     */
    @Binds
    @Singleton
    abstract fun bindRomVerificationManager(
        romVerificationManagerImpl: RomVerificationManagerImpl
    ): RomVerificationManager

    /**
     * Binds BackupManagerImpl to the BackupManager interface for dependency injection.
     *
     * @param backupManagerImpl Implementation instance to be used when injecting BackupManager.
     * @return The bound BackupManager implementation.
     */
    @Binds
    @Singleton
    abstract fun bindBackupManager(
        backupManagerImpl: BackupManagerImpl
    ): BackupManager

    companion object {

        /**
         * Provides the absolute path to the RomTools data directory inside the application's files directory.
         *
         * @return The absolute path to the "romtools" directory located under the application's files directory.
         */
        @Provides
        @Singleton
        @RomToolsDataDir
        fun provideRomToolsDataDirectory(
            @ApplicationContext context: Context
        ): String {
            return "${context.filesDir}/romtools"
        }

        /**
         * Provides the file-system path for the ROM tools backups directory.
         *
         * @return The absolute path to the "backups" directory located inside the app's external files directory.
         */
        @Provides
        @Singleton
        @RomToolsBackupDir
        fun provideRomToolsBackupDirectory(
            @ApplicationContext context: Context
        ): String {
            return "${context.getExternalFilesDir(null)}/backups"
        }

        /**
         * Provides the path for the ROM tools downloads directory located in the app's external files.
         *
         * @return The file-system path to the downloads directory inside the application's external files directory.
         */
        @Provides
        @Singleton
        @RomToolsDownloadDir
        fun provideRomToolsDownloadDirectory(
            @ApplicationContext context: Context
        ): String {
            return "${context.getExternalFilesDir(null)}/downloads"
        }

        /**
         * Provides the path to the ROM tools temporary directory inside the application's cache.
         *
         * @return The absolute path to the temp directory, e.g. "<app_cache>/romtools_temp".
         */
        @Provides
        @Singleton
        @RomToolsTempDir
        fun provideRomToolsTempDirectory(
            @ApplicationContext context: Context
        ): String {
            return "${context.cacheDir}/romtools_temp"
        }
    }
}

// Qualifier annotations for ROM tools directories
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsDataDir

/**
 * Qualifier for the backup directory for the ROM tools.
 */
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsBackupDir

/**
 * Qualifier for the download directory for the ROM tools.
 */
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsDownloadDir

/**
 * Qualifier for the temporary directory for the ROM tools.
 */
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsTempDir
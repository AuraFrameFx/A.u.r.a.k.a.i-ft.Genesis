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
import dev.aurakai.auraframefx.romtools.retention.AurakaiRetentionManager
import dev.aurakai.auraframefx.romtools.retention.AurakaiRetentionManagerImpl
import javax.inject.Singleton

/**
 * ROM Tools Hilt module providing dependency injection bindings.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RomToolsModule {

    @Binds
    @Singleton
    abstract fun bindBootloaderManager(
        bootloaderManagerImpl: BootloaderManagerImpl
    ): BootloaderManager

    @Binds
    @Singleton
    abstract fun bindRecoveryManager(
        recoveryManagerImpl: RecoveryManagerImpl
    ): RecoveryManager

    @Binds
    @Singleton
    abstract fun bindSystemModificationManager(
        systemModificationManagerImpl: SystemModificationManagerImpl
    ): SystemModificationManager

    @Binds
    @Singleton
    abstract fun bindFlashManager(
        flashManagerImpl: FlashManagerImpl
    ): FlashManager

    @Binds
    @Singleton
    abstract fun bindRomVerificationManager(
        romVerificationManagerImpl: RomVerificationManagerImpl
    ): RomVerificationManager

    @Binds
    @Singleton
    abstract fun bindBackupManager(
        backupManagerImpl: BackupManagerImpl
    ): BackupManager

    @Binds
    @Singleton
    abstract fun bindAurakaiRetentionManager(
        aurakaiRetentionManagerImpl: AurakaiRetentionManagerImpl
    ): AurakaiRetentionManager

    companion object {

        @Provides
        @Singleton
        @RomToolsDataDir
        fun provideRomToolsDataDirectory(
            @ApplicationContext context: Context
        ): String {
            return "${context.filesDir}/romtools"
        }

        @Provides
        @Singleton
        @RomToolsBackupDir
        fun provideRomToolsBackupDirectory(
            @ApplicationContext context: Context
        ): String {
            return "${context.getExternalFilesDir(null)}/backups"
        }

        @Provides
        @Singleton
        @RomToolsDownloadDir
        fun provideRomToolsDownloadDirectory(
            @ApplicationContext context: Context
        ): String {
            return "${context.getExternalFilesDir(null)}/downloads"
        }

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

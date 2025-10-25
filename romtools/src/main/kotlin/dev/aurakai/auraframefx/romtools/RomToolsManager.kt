// File: romtools/src/main/kotlin/dev/aurakai/auraframefx/romtools/RomToolsManager.kt
package dev.aurakai.auraframefx.romtools

import android.os.Build
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import dev.aurakai.auraframefx.romtools.recovery.RecoveryManager
import dev.aurakai.auraframefx.romtools.system.SystemModificationManager
import dev.aurakai.auraframefx.romtools.flash.FlashManager
import dev.aurakai.auraframefx.romtools.verification.RomVerificationManager
import dev.aurakai.auraframefx.romtools.backup.BackupManager
import dev.aurakai.auraframefx.romtools.retention.AurakaiRetentionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ROM Tools Manager - Genesis Protocol
 *
 * Manages ROM flashing, backup/restore, bootloader operations,
 * and Genesis AI system optimizations.
 *
 * RIDE OR DIE - This is the real implementation!
 */
@Singleton
class RomToolsManager @Inject constructor(
    private val bootloaderManager: BootloaderManager,
    private val recoveryManager: RecoveryManager,
    private val systemModificationManager: SystemModificationManager,
    private val flashManager: FlashManager,
    private val verificationManager: RomVerificationManager,
    private val backupManager: BackupManager,
    private val retentionManager: AurakaiRetentionManager
) {
    private val _romToolsState = MutableStateFlow(RomToolsState())
    val romToolsState: StateFlow<RomToolsState> = _romToolsState

    private val _operationProgress = MutableStateFlow<OperationProgress?>(null)
    val operationProgress: StateFlow<OperationProgress?> = _operationProgress

    init {
        Timber.i("ROM Tools Manager initialized")
        checkRomToolsCapabilities()
    }

    /**
     * Evaluates the device's ROM-related capabilities and updates the manager's state.
     *
     * Builds a RomCapabilities object (root, bootloader, recovery, system write access, supported architectures,
     * device model, Android version, and security patch level), stores it in the internal RomToolsState, and
     * marks the manager as initialized.
     */
    private fun checkRomToolsCapabilities() {
        val deviceInfo = DeviceInfo.getCurrentDevice()
        val capabilities = RomCapabilities(
            hasRootAccess = checkRootAccess(),
            hasBootloaderAccess = bootloaderManager.checkBootloaderAccess(),
            hasRecoveryAccess = recoveryManager.checkRecoveryAccess(),
            hasSystemWriteAccess = systemModificationManager.checkSystemWriteAccess(),
            supportedArchitectures = getSupportedArchitectures(),
            deviceModel = deviceInfo.model,
            androidVersion = deviceInfo.androidVersion,
            securityPatchLevel = deviceInfo.securityPatchLevel
        )

        _romToolsState.value = _romToolsState.value.copy(
            capabilities = capabilities,
            isInitialized = true
        )

        Timber.i("ROM capabilities checked: $capabilities")
    }

    /**
     * Orchestrates flashing a custom ROM to the device while preserving Aurakai retention.
     *
     * Performs verification, optional NANDroid backup, bootloader/recovery preparation, flashing with progress updates, post-flash verification, and automatic Aurakai restoration.
     *
     * @param romFile The ROM file metadata to flash (name, path, size, checksum).
     * @return A `Result` containing `Unit` on success, or a failure with the encountered exception on error.
     */
    suspend fun flashRom(romFile: RomFile): Result<Unit> {
        return try {
            updateOperationProgress(RomOperation.FLASHING_ROM, 0f)

            // Step 0: 🛡️ Setup Aurakai retention mechanisms (CRITICAL!)
            // If this fails, we ABORT the ROM flash for safety
            updateOperationProgress(RomOperation.SETTING_UP_RETENTION, 5f)
            val retentionResult = retentionManager.setupRetentionMechanisms()
            if (retentionResult.isFailure) {
                val error = retentionResult.exceptionOrNull()
                Timber.e(error, "🚨 Retention setup failed - ABORTING ROM flash for safety!")
                throw Exception("Retention mechanism setup failed. ROM flash aborted to prevent losing Aurakai.", error)
            }
            val retentionStatus = retentionResult.getOrThrow()
            Timber.i("🛡️ Retention mechanisms active: ${retentionStatus.mechanisms}")

            // Step 1: Verify ROM file integrity
            updateOperationProgress(RomOperation.VERIFYING_ROM, 10f)
            verificationManager.verifyRomFile(romFile).getOrThrow()

            // Step 2: Create backup if requested
            if (_romToolsState.value.settings.autoBackup) {
                updateOperationProgress(RomOperation.CREATING_BACKUP, 20f)
                backupManager.createFullBackup().getOrThrow()
            }

            // Step 3: Unlock bootloader if needed
            if (!bootloaderManager.isBootloaderUnlocked()) {
                updateOperationProgress(RomOperation.UNLOCKING_BOOTLOADER, 30f)
                bootloaderManager.unlockBootloader().getOrThrow()
            }

            // Step 4: Install custom recovery if needed
            if (!recoveryManager.isCustomRecoveryInstalled()) {
                updateOperationProgress(RomOperation.INSTALLING_RECOVERY, 40f)
                recoveryManager.installCustomRecovery().getOrThrow()
            }

            // Step 5: Flash ROM
            updateOperationProgress(RomOperation.FLASHING_ROM, 50f)
            flashManager.flashRom(romFile) { progress ->
                updateOperationProgress(RomOperation.FLASHING_ROM, 50f + (progress * 35f))
            }.getOrThrow()

            // Step 6: Verify installation
            updateOperationProgress(RomOperation.VERIFYING_INSTALLATION, 85f)
            verificationManager.verifyInstallation().getOrThrow()

            // Step 7: 🔄 Restore Aurakai after ROM flash (NO SETUP REQUIRED!)
            updateOperationProgress(RomOperation.RESTORING_AURAKAI, 90f)
            retentionManager.restoreAurakaiAfterRomFlash().getOrThrow()
            Timber.i("🔄 Aurakai restored successfully - no reinstall needed!")

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("✅ ROM flashed successfully: ${romFile.name} - Aurakai retained!")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e(e, "Failed to flash ROM: ${romFile.name}")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Creates a NANDroid backup of the current ROM and tracks operation progress.
     *
     * @param backupName The name to assign to the created backup.
     * @return A `Result` containing the created `BackupInfo` on success, or a failure wrapping the exception on error.
     */
    suspend fun createNandroidBackup(backupName: String): Result<BackupInfo> {
        return try {
            updateOperationProgress(RomOperation.CREATING_BACKUP, 0f)

            val backupInfo = backupManager.createNandroidBackup(backupName) { progress ->
                updateOperationProgress(RomOperation.CREATING_BACKUP, progress)
            }.getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("NANDroid backup created: $backupName")
            Result.success(backupInfo)

        } catch (e: Exception) {
            Timber.e(e, "Failed to create NANDroid backup: $backupName")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Restores device partitions from the provided NANDroid backup.
     *
     * @param backupInfo Metadata identifying the backup to restore.
     * @return A `Result` containing `Unit` on success, or a failed `Result` with the error on failure.
     */
    suspend fun restoreNandroidBackup(backupInfo: BackupInfo): Result<Unit> {
        return try {
            updateOperationProgress(RomOperation.RESTORING_BACKUP, 0f)

            backupManager.restoreNandroidBackup(backupInfo) { progress ->
                updateOperationProgress(RomOperation.RESTORING_BACKUP, progress)
            }.getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("NANDroid backup restored: ${backupInfo.name}")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e(e, "Failed to restore NANDroid backup: ${backupInfo.name}")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Apply Genesis AI system optimization patches.
     *
     * Updates the exposed operation progress using `RomOperation.APPLYING_OPTIMIZATIONS` while running and clears progress on completion or failure.
     *
     * @return A `Result` that is successful with `Unit` when optimizations were applied, or a failed `Result` containing the encountered exception.
     */
    suspend fun installGenesisOptimizations(): Result<Unit> {
        return try {
            updateOperationProgress(RomOperation.APPLYING_OPTIMIZATIONS, 0f)

            systemModificationManager.installGenesisOptimizations { progress ->
                updateOperationProgress(RomOperation.APPLYING_OPTIMIZATIONS, progress)
            }.getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("Genesis AI optimizations installed successfully")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e(e, "Failed to install Genesis optimizations")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Provides a list of available custom ROMs compatible with the current device.
     *
     * @return `Result` containing the list of compatible `AvailableRom` on success, or a failed `Result` with the encountered exception.
     */
    suspend fun getAvailableRoms(): Result<List<AvailableRom>> {
        return try {
            val deviceModel = _romToolsState.value.capabilities?.deviceModel ?: "unknown"
            val roms = romRepository.getCompatibleRoms(deviceModel)
            Result.success(roms)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get available ROMs")
            Result.failure(e)
        }
    }

    /**
     * Initiates download of the specified ROM and emits progress updates.
     *
     * @param rom The ROM metadata to download.
     * @return A Flow that emits DownloadProgress updates for the download operation.
     */
    suspend fun downloadRom(rom: AvailableRom): Flow<DownloadProgress> {
        return flashManager.downloadRom(rom)
    }

    /**
     * Initializes Aurakai retention mechanisms so Aurakai survives ROM operations without performing a full flash.
     *
     * @return `Result<dev.aurakai.auraframefx.romtools.retention.RetentionStatus>` containing the retention status on success, or a failure wrapping the encountered exception.
     */
    suspend fun setupAurakaiRetention(): Result<dev.aurakai.auraframefx.romtools.retention.RetentionStatus> {
        return try {
            updateOperationProgress(RomOperation.SETTING_UP_RETENTION, 0f)

            val retentionStatus = retentionManager.setupRetentionMechanisms().getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("🛡️ Aurakai retention mechanisms setup complete")
            Result.success(retentionStatus)

        } catch (e: Exception) {
            Timber.e(e, "Failed to setup Aurakai retention")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Update the current operation progress stored in the manager.
     *
     * @param operation The ROM operation being reported.
     * @param progress Progress value between 0.0 and 1.0 where 0.0 is start and 1.0 is complete.
     */
    private fun updateOperationProgress(operation: RomOperation, progress: Float) {
        _operationProgress.value = OperationProgress(operation, progress)
    }

    /**
     * Clears the current operation progress.
     *
     * Sets the internal operation progress state to `null`, removing any active progress indicator.
     */
    private fun clearOperationProgress() {
        _operationProgress.value = null
    }

    /**
     * Checks whether the device grants root (su) access.
     *
     * @return `true` if a root shell can be executed, `false` otherwise.
     */
    private fun checkRootAccess(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su -c 'echo test'")
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Lists the CPU ABIs supported by the current device.
     *
     * @return A list of supported CPU ABI strings in preferred order.
     */
    private fun getSupportedArchitectures(): List<String> {
        return Build.SUPPORTED_ABIS.toList()
    }

    // Companion object for static access
    companion object {
        private val romRepository = RomRepository()
    }
}

// Data classes
data class RomToolsState(
    val capabilities: RomCapabilities? = null,
    val isInitialized: Boolean = false,
    val settings: RomToolsSettings = RomToolsSettings(),
    val availableRoms: List<AvailableRom> = emptyList(),
    val backups: List<BackupInfo> = emptyList()
)

data class RomCapabilities(
    val hasRootAccess: Boolean,
    val hasBootloaderAccess: Boolean,
    val hasRecoveryAccess: Boolean,
    val hasSystemWriteAccess: Boolean,
    val supportedArchitectures: List<String>,
    val deviceModel: String,
    val androidVersion: String,
    val securityPatchLevel: String
)

data class RomToolsSettings(
    val autoBackup: Boolean = true,
    val verifyRomSignatures: Boolean = true,
    val enableGenesisOptimizations: Boolean = true,
    val maxBackupCount: Int = 5,
    val downloadDirectory: String = ""
)

data class OperationProgress(
    val operation: RomOperation,
    val progress: Float
)

/**
 * Represents the different types of ROM operations.
 */
enum class RomOperation {
    SETTING_UP_RETENTION,      // 🛡️ Setting up Aurakai retention mechanisms
    VERIFYING_ROM,
    CREATING_BACKUP,
    UNLOCKING_BOOTLOADER,
    INSTALLING_RECOVERY,
    FLASHING_ROM,
    VERIFYING_INSTALLATION,
    RESTORING_AURAKAI,         // 🔄 Restoring Aurakai after ROM flash
    RESTORING_BACKUP,
    APPLYING_OPTIMIZATIONS,
    DOWNLOADING_ROM,
    COMPLETED,
    FAILED
}

data class RomFile(
    val name: String,
    val path: String,
    val size: Long = 0L,
    val checksum: String = ""
)

data class DeviceInfo(
    val model: String,
    val manufacturer: String,
    val androidVersion: String,
    val securityPatchLevel: String,
    val bootloaderVersion: String
) {
    companion object {
        /**
         * Creates a DeviceInfo populated with the current device's build information.
         *
         * @return A DeviceInfo containing the device model, manufacturer, Android version, security patch level, and bootloader version.
         */
        fun getCurrentDevice(): DeviceInfo {
            return DeviceInfo(
                model = Build.MODEL,
                manufacturer = Build.MANUFACTURER,
                androidVersion = Build.VERSION.RELEASE,
                securityPatchLevel = Build.VERSION.SECURITY_PATCH,
                bootloaderVersion = Build.BOOTLOADER
            )
        }
    }
}

data class BackupInfo(
    val name: String,
    val path: String,
    val size: Long,
    val createdAt: Long,
    val deviceModel: String,
    val androidVersion: String,
    val partitions: List<String>
)

data class AvailableRom(
    val name: String,
    val version: String,
    val androidVersion: String,
    val downloadUrl: String,
    val size: Long,
    val checksum: String
)

data class DownloadProgress(
    val bytesDownloaded: Long,
    val totalBytes: Long,
    val progress: Float,
    val speed: Long,
    val isCompleted: Boolean = false,
    val error: String? = null
)

class RomRepository {
    /**
     * Retrieves available ROMs that are compatible with the given device model.
     *
     * @param deviceModel The device model identifier used to filter compatible ROMs (e.g., "Pixel 6").
     * @return A list of AvailableRom entries that match the specified device model; empty if none are found.
     */
    fun getCompatibleRoms(deviceModel: String): List<AvailableRom> {
        // Would query online repository - placeholder for now
        return emptyList()
    }
}
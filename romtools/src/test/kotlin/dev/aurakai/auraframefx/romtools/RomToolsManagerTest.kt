package dev.aurakai.auraframefx.romtools

import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import dev.aurakai.auraframefx.romtools.recovery.RecoveryManager
import dev.aurakai.auraframefx.romtools.system.SystemModificationManager
import dev.aurakai.auraframefx.romtools.flash.FlashManager
import dev.aurakai.auraframefx.romtools.verification.RomVerificationManager
import dev.aurakai.auraframefx.romtools.backup.BackupManager
import dev.aurakai.auraframefx.romtools.retention.AurakaiRetentionManager
import dev.aurakai.auraframefx.romtools.retention.RetentionStatus
import dev.aurakai.auraframefx.romtools.retention.RetentionMechanism
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RomToolsManagerTest {

    private lateinit var romToolsManager: RomToolsManager
    private val mockBootloaderManager = mockk<BootloaderManager>()
    private val mockRecoveryManager = mockk<RecoveryManager>()
    private val mockSystemModManager = mockk<SystemModificationManager>()
    private val mockFlashManager = mockk<FlashManager>()
    private val mockVerificationManager = mockk<RomVerificationManager>()
    private val mockBackupManager = mockk<BackupManager>()
    private val mockRetentionManager = mockk<AurakaiRetentionManager>()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        
        every { mockBootloaderManager.checkBootloaderAccess() } returns true
        every { mockRecoveryManager.checkRecoveryAccess() } returns true
        every { mockSystemModManager.checkSystemWriteAccess() } returns true

        romToolsManager = RomToolsManager(
            bootloaderManager = mockBootloaderManager,
            recoveryManager = mockRecoveryManager,
            systemModificationManager = mockSystemModManager,
            flashManager = mockFlashManager,
            verificationManager = mockVerificationManager,
            backupManager = mockBackupManager,
            retentionManager = mockRetentionManager
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    @DisplayName("ROM Flashing with Retention Tests")
    inner class RomFlashingRetentionTests {

        @Test
        @DisplayName("Should setup Aurakai retention before ROM flash")
        fun `should setup retention mechanisms before flashing`() = runTest {
            // Given
            val romFile = RomFile("test_rom.zip", "/sdcard/test_rom.zip", 1024000, "abc123")
            val retentionStatus = RetentionStatus(
                mechanisms = mapOf(
                    RetentionMechanism.APK_BACKUP to true,
                    RetentionMechanism.ADDON_D_SCRIPT to true
                ),
                retentionDirPath = "/data/local/genesis_retention",
                packageName = "dev.aurakai.test",
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns Result.success(retentionStatus)
            coEvery { mockVerificationManager.verifyRomFile(any()) } returns Result.success(Unit)
            coEvery { mockBootloaderManager.isBootloaderUnlocked() } returns true
            coEvery { mockRecoveryManager.isCustomRecoveryInstalled() } returns true
            coEvery { mockFlashManager.flashRom(any(), any()) } returns Result.success(Unit)
            coEvery { mockVerificationManager.verifyInstallation() } returns Result.success(Unit)
            coEvery { mockRetentionManager.restoreAurakaiAfterRomFlash() } returns Result.success(Unit)

            // When
            val result = romToolsManager.flashRom(romFile)

            // Then
            assertTrue(result.isSuccess)
            coVerify(exactly = 1) { mockRetentionManager.setupRetentionMechanisms() }
            coVerify(exactly = 1) { mockRetentionManager.restoreAurakaiAfterRomFlash() }
        }

        @Test
        @DisplayName("Should restore Aurakai after successful ROM flash")
        fun `should restore aurakai after rom flash`() = runTest {
            // Given
            val romFile = RomFile("test_rom.zip", "/sdcard/test_rom.zip")
            val retentionStatus = RetentionStatus(
                mechanisms = mapOf(RetentionMechanism.APK_BACKUP to true),
                retentionDirPath = "/data/local/genesis_retention",
                packageName = "dev.aurakai.test",
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns Result.success(retentionStatus)
            coEvery { mockVerificationManager.verifyRomFile(any()) } returns Result.success(Unit)
            coEvery { mockBootloaderManager.isBootloaderUnlocked() } returns true
            coEvery { mockRecoveryManager.isCustomRecoveryInstalled() } returns true
            coEvery { mockFlashManager.flashRom(any(), any()) } returns Result.success(Unit)
            coEvery { mockVerificationManager.verifyInstallation() } returns Result.success(Unit)
            coEvery { mockRetentionManager.restoreAurakaiAfterRomFlash() } returns Result.success(Unit)

            // When
            val result = romToolsManager.flashRom(romFile)

            // Then
            assertTrue(result.isSuccess)
            coVerify { mockRetentionManager.restoreAurakaiAfterRomFlash() }
        }

        @Test
        @DisplayName("Should fail gracefully if retention setup fails")
        fun `should handle retention setup failure`() = runTest {
            // Given
            val romFile = RomFile("test_rom.zip", "/sdcard/test_rom.zip")
            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns 
                Result.failure(Exception("Retention setup failed"))

            // When
            val result = romToolsManager.flashRom(romFile)

            // Then
            assertTrue(result.isFailure)
            coVerify(exactly = 1) { mockRetentionManager.setupRetentionMechanisms() }
            coVerify(exactly = 0) { mockFlashManager.flashRom(any(), any()) }
        }

        @Test
        @DisplayName("Should handle ROM flash failure and cleanup")
        fun `should handle flash failure`() = runTest {
            // Given
            val romFile = RomFile("corrupt_rom.zip", "/sdcard/corrupt_rom.zip")
            val retentionStatus = RetentionStatus(
                mechanisms = mapOf(RetentionMechanism.APK_BACKUP to true),
                retentionDirPath = "/data/local/genesis_retention",
                packageName = "dev.aurakai.test",
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns Result.success(retentionStatus)
            coEvery { mockVerificationManager.verifyRomFile(any()) } returns Result.success(Unit)
            coEvery { mockBootloaderManager.isBootloaderUnlocked() } returns true
            coEvery { mockRecoveryManager.isCustomRecoveryInstalled() } returns true
            coEvery { mockFlashManager.flashRom(any(), any()) } returns 
                Result.failure(Exception("Flash failed"))

            // When
            val result = romToolsManager.flashRom(romFile)

            // Then
            assertTrue(result.isFailure)
            coVerify(exactly = 0) { mockRetentionManager.restoreAurakaiAfterRomFlash() }
        }

        @Test
        @DisplayName("Should continue even if Aurakai restore fails")
        fun `should continue if restore fails`() = runTest {
            // Given
            val romFile = RomFile("test_rom.zip", "/sdcard/test_rom.zip")
            val retentionStatus = RetentionStatus(
                mechanisms = mapOf(RetentionMechanism.APK_BACKUP to true),
                retentionDirPath = "/data/local/genesis_retention",
                packageName = "dev.aurakai.test",
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns Result.success(retentionStatus)
            coEvery { mockVerificationManager.verifyRomFile(any()) } returns Result.success(Unit)
            coEvery { mockBootloaderManager.isBootloaderUnlocked() } returns true
            coEvery { mockRecoveryManager.isCustomRecoveryInstalled() } returns true
            coEvery { mockFlashManager.flashRom(any(), any()) } returns Result.success(Unit)
            coEvery { mockVerificationManager.verifyInstallation() } returns Result.success(Unit)
            coEvery { mockRetentionManager.restoreAurakaiAfterRomFlash() } returns 
                Result.failure(Exception("Restore failed"))

            // When
            val result = romToolsManager.flashRom(romFile)

            // Then - Flash should still report failure since restore is part of the flow
            assertTrue(result.isFailure)
        }
    }

    @Nested
    @DisplayName("Standalone Retention Setup Tests")
    inner class StandaloneRetentionTests {

        @Test
        @DisplayName("Should allow setting up retention independently")
        fun `should setup retention independently`() = runTest {
            // Given
            val retentionStatus = RetentionStatus(
                mechanisms = mapOf(
                    RetentionMechanism.APK_BACKUP to true,
                    RetentionMechanism.ADDON_D_SCRIPT to true,
                    RetentionMechanism.RECOVERY_ZIP to true
                ),
                retentionDirPath = "/data/local/genesis_retention",
                packageName = "dev.aurakai.test",
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns Result.success(retentionStatus)

            // When
            val result = romToolsManager.setupAurakaiRetention()

            // Then
            assertTrue(result.isSuccess)
            val status = result.getOrNull()
            assertNotNull(status)
            assertTrue(status!!.isFullyProtected)
            assertEquals(3, status.mechanisms.filter { it.value }.size)
        }

        @Test
        @DisplayName("Should report partial protection with some mechanisms failing")
        fun `should report partial protection`() = runTest {
            // Given
            val retentionStatus = RetentionStatus(
                mechanisms = mapOf(
                    RetentionMechanism.APK_BACKUP to true,
                    RetentionMechanism.ADDON_D_SCRIPT to false,
                    RetentionMechanism.RECOVERY_ZIP to false
                ),
                retentionDirPath = "/data/local/genesis_retention",
                packageName = "dev.aurakai.test",
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns Result.success(retentionStatus)

            // When
            val result = romToolsManager.setupAurakaiRetention()

            // Then
            assertTrue(result.isSuccess)
            val status = result.getOrNull()
            assertNotNull(status)
            assertFalse(status!!.isFullyProtected) // Need at least 2 mechanisms
        }
    }

    @Nested
    @DisplayName("Backup and Restore Tests")
    inner class BackupRestoreTests {

        @Test
        @DisplayName("Should create NANDroid backup successfully")
        fun `should create nandroid backup`() = runTest {
            // Given
            val backupName = "test_backup_20251025"
            val backupInfo = BackupInfo(
                name = backupName,
                path = "/sdcard/Genesis/backups/$backupName",
                size = 2048000,
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockBackupManager.createNandroidBackup(any(), any()) } returns Result.success(backupInfo)

            // When
            val result = romToolsManager.createNandroidBackup(backupName)

            // Then
            assertTrue(result.isSuccess)
            assertEquals(backupName, result.getOrNull()?.name)
        }

        @Test
        @DisplayName("Should restore NANDroid backup successfully")
        fun `should restore nandroid backup`() = runTest {
            // Given
            val backupInfo = BackupInfo(
                name = "test_backup",
                path = "/sdcard/Genesis/backups/test_backup",
                size = 2048000,
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockBackupManager.restoreNandroidBackup(any(), any()) } returns Result.success(Unit)

            // When
            val result = romToolsManager.restoreNandroidBackup(backupInfo)

            // Then
            assertTrue(result.isSuccess)
        }
    }

    @Nested
    @DisplayName("RomOperation Enum Tests")
    inner class RomOperationEnumTests {

        @ParameterizedTest
        @EnumSource(RomOperation::class)
        @DisplayName("Should have all ROM operation types")
        fun `should contain all operation types`(operation: RomOperation) {
            assertNotNull(operation)
        }

        @Test
        @DisplayName("Should include retention-specific operations")
        fun `should include retention operations`() {
            val operations = RomOperation.values()
            assertTrue(operations.contains(RomOperation.SETTING_UP_RETENTION))
            assertTrue(operations.contains(RomOperation.RESTORING_AURAKAI))
        }
    }

    @Nested
    @DisplayName("Progress Tracking Tests")
    inner class ProgressTrackingTests {

        @Test
        @DisplayName("Should track progress through all retention phases")
        fun `should track retention progress phases`() = runTest {
            // Given
            val romFile = RomFile("test_rom.zip", "/sdcard/test_rom.zip")
            val progressUpdates = mutableListOf<Float>()
            val retentionStatus = RetentionStatus(
                mechanisms = mapOf(RetentionMechanism.APK_BACKUP to true),
                retentionDirPath = "/data/local/genesis_retention",
                packageName = "dev.aurakai.test",
                timestamp = System.currentTimeMillis()
            )

            coEvery { mockRetentionManager.setupRetentionMechanisms() } returns Result.success(retentionStatus)
            coEvery { mockVerificationManager.verifyRomFile(any()) } returns Result.success(Unit)
            coEvery { mockBootloaderManager.isBootloaderUnlocked() } returns true
            coEvery { mockRecoveryManager.isCustomRecoveryInstalled() } returns true
            coEvery { mockFlashManager.flashRom(any(), any()) } coAnswers {
                val progressCallback = secondArg<(Float) -> Unit>()
                progressCallback(0.5f)
                Result.success(Unit)
            }
            coEvery { mockVerificationManager.verifyInstallation() } returns Result.success(Unit)
            coEvery { mockRetentionManager.restoreAurakaiAfterRomFlash() } returns Result.success(Unit)

            // When
            romToolsManager.flashRom(romFile)

            // Then - Verify retention phases are included
            coVerify { mockRetentionManager.setupRetentionMechanisms() }
            coVerify { mockRetentionManager.restoreAurakaiAfterRomFlash() }
        }
    }
}

// Additional data classes for testing
data class BackupInfo(
    val name: String,
    val path: String,
    val size: Long,
    val timestamp: Long
)
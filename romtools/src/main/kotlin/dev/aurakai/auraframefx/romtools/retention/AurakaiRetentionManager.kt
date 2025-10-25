// File: romtools/src/main/kotlin/dev/aurakai/auraframefx/romtools/retention/AurakaiRetentionManager.kt
package dev.aurakai.auraframefx.romtools.retention

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Aurakai Retention Manager - Genesis Protocol
 *
 * Ensures Aurakai application survives ROM flashing, bootloader operations,
 * and system modifications. No setup required after ROM installation!
 *
 * Retention mechanisms:
 * 1. Addon.d survival scripts (LineageOS/AOSP ROMs)
 * 2. Pre-flash APK + data backup
 * 3. Post-flash automatic restore
 * 4. Recovery flashable zip generation
 * 5. Magisk module integration (if available)
 */
@Singleton
class AurakaiRetentionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val packageName = context.packageName
    private val retentionDir = File("/data/local/genesis_retention")
    private val addonDDir = File("/system/addon.d")
    private val recoveryZipDir = File("/sdcard/Genesis/recovery_zips")

    init {
        Timber.i("🛡️ AurakaiRetentionManager initialized - package: $packageName")
    }

    /**
     * Set up all retention mechanisms before ROM operations.
     * This ensures Aurakai survives the flash process.
     */
    suspend fun setupRetentionMechanisms(): Result<RetentionStatus> {
        return try {
            Timber.i("🛡️ Setting up Aurakai retention mechanisms...")

            val results = mutableListOf<RetentionMechanism>()

            // 1. Create retention directory
            if (!retentionDir.exists()) {
                retentionDir.mkdirs()
                executeRootCommand("chmod 755 ${retentionDir.absolutePath}")
            }

            // 2. Backup APK and data
            val backupResult = backupAurakaiApkAndData()
            results.add(RetentionMechanism.APK_BACKUP to backupResult.isSuccess)

            // 3. Install addon.d survival script
            val addonDResult = installAddonDScript()
            results.add(RetentionMechanism.ADDON_D_SCRIPT to addonDResult.isSuccess)

            // 4. Generate recovery flashable zip
            val recoveryZipResult = generateRecoveryFlashableZip()
            results.add(RetentionMechanism.RECOVERY_ZIP to recoveryZipResult.isSuccess)

            // 5. Create Magisk module if Magisk is detected
            if (isMagiskInstalled()) {
                val magiskResult = createMagiskModule()
                results.add(RetentionMechanism.MAGISK_MODULE to magiskResult.isSuccess)
            }

            val status = RetentionStatus(
                mechanisms = results.toMap(),
                retentionDirPath = retentionDir.absolutePath,
                packageName = packageName,
                timestamp = System.currentTimeMillis()
            )

            Timber.i("🛡️ Retention setup complete: $status")
            Result.success(status)

        } catch (e: Exception) {
            Timber.e(e, "Failed to setup retention mechanisms")
            Result.failure(e)
        }
    }

    /**
     * Backup Aurakai APK and application data.
     */
    private suspend fun backupAurakaiApkAndData(): Result<BackupPaths> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
            val apkPath = packageInfo.applicationInfo.sourceDir
            val dataDir = context.dataDir

            // Backup APK
            val apkBackupPath = File(retentionDir, "aurakai.apk")
            executeRootCommand("cp $apkPath ${apkBackupPath.absolutePath}")

            // Backup app data (excluding cache)
            val dataBackupPath = File(retentionDir, "aurakai_data.tar.gz")
            executeRootCommand(
                "tar -czf ${dataBackupPath.absolutePath} " +
                "-C ${dataDir.parent} " +
                "--exclude='cache' --exclude='code_cache' " +
                "${dataDir.name}"
            )

            // Backup shared prefs specifically (critical for Genesis state)
            val sharedPrefsDir = File(dataDir, "shared_prefs")
            val prefsBackupPath = File(retentionDir, "aurakai_prefs.tar.gz")
            if (sharedPrefsDir.exists()) {
                executeRootCommand("tar -czf ${prefsBackupPath.absolutePath} -C ${dataDir.absolutePath} shared_prefs")
            }

            Timber.i("✅ Aurakai APK and data backed up successfully")

            Result.success(
                BackupPaths(
                    apkPath = apkBackupPath.absolutePath,
                    dataPath = dataBackupPath.absolutePath,
                    prefsPath = prefsBackupPath.absolutePath
                )
            )

        } catch (e: Exception) {
            Timber.e(e, "Failed to backup Aurakai APK and data")
            Result.failure(e)
        }
    }

    /**
     * Install addon.d survival script for LineageOS and AOSP-based ROMs.
     * This script runs during ROM updates to preserve Aurakai.
     */
    private suspend fun installAddonDScript(): Result<String> {
        return try {
            if (!addonDDir.exists()) {
                Timber.w("addon.d directory not found - ROM may not support addon.d scripts")
                return Result.failure(Exception("addon.d not supported on this ROM"))
            }

            val scriptPath = File(addonDDir, "99-aurakai.sh")
            val scriptContent = generateAddonDScript()

            // Write script to temporary location first
            val tempScript = File(retentionDir, "99-aurakai.sh")
            tempScript.writeText(scriptContent)

            // Copy to /system/addon.d with root
            executeRootCommand("mount -o remount,rw /system")
            executeRootCommand("cp ${tempScript.absolutePath} ${scriptPath.absolutePath}")
            executeRootCommand("chmod 755 ${scriptPath.absolutePath}")
            executeRootCommand("chown root:root ${scriptPath.absolutePath}")
            executeRootCommand("mount -o remount,ro /system")

            Timber.i("✅ Addon.d survival script installed: ${scriptPath.absolutePath}")
            Result.success(scriptPath.absolutePath)

        } catch (e: Exception) {
            Timber.e(e, "Failed to install addon.d script")
            Result.failure(e)
        }
    }

    /**
     * Generate addon.d shell script for ROM survival.
     */
    private fun generateAddonDScript(): String {
        return """
#!/sbin/sh
#
# /system/addon.d/99-aurakai.sh
# Aurakai Genesis AI survival script for ROM updates
# This ensures Aurakai persists through ROM flashing
#

. /tmp/backuptool.functions

list_files() {
cat <<EOF
app/Aurakai/Aurakai.apk
EOF
}

case "${'$'}1" in
  backup)
    list_files | while read FILE REPLACEMENT; do
      backup_file ${'$'}S/"${'$'}FILE"
    done

    # Backup Aurakai data separately
    if [ -d /data/data/$packageName ]; then
      tar -czf /tmp/aurakai_data_backup.tar.gz -C /data/data $packageName
    fi
  ;;
  restore)
    list_files | while read FILE REPLACEMENT; do
      R=""
      [ -n "${'$'}REPLACEMENT" ] && R="${'$'}S/${'$'}REPLACEMENT"
      [ -f "${'$'}C/${'$'}S/${'$'}FILE" ] && restore_file ${'$'}S/"${'$'}FILE" "${'$'}R"
    done

    # Restore Aurakai data
    if [ -f /tmp/aurakai_data_backup.tar.gz ]; then
      tar -xzf /tmp/aurakai_data_backup.tar.gz -C /data/data
      chown -R $(stat -c '%u:%g' /data/data/$packageName) /data/data/$packageName
      rm -f /tmp/aurakai_data_backup.tar.gz
    fi
  ;;
  pre-backup)
    # Stub
  ;;
  post-backup)
    # Stub
  ;;
  pre-restore)
    # Stub
  ;;
  post-restore)
    # Stub
    # Fix permissions after restore
    if [ -d /data/data/$packageName ]; then
      pm install -r /system/app/Aurakai/Aurakai.apk
      restorecon -R /data/data/$packageName
    fi
  ;;
esac
        """.trimIndent()
    }

    /**
     * Generate a recovery flashable zip that installs Aurakai.
     * Can be flashed after ROM installation to restore the app.
     */
    private suspend fun generateRecoveryFlashableZip(): Result<String> {
        return try {
            if (!recoveryZipDir.exists()) {
                recoveryZipDir.mkdirs()
            }

            val zipFile = File(recoveryZipDir, "aurakai_installer_${System.currentTimeMillis()}.zip")
            val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
            val apkPath = packageInfo.applicationInfo.sourceDir

            ZipOutputStream(zipFile.outputStream()).use { zip ->
                // Add APK
                zip.putNextEntry(ZipEntry("system/app/Aurakai/Aurakai.apk"))
                File(apkPath).inputStream().use { it.copyTo(zip) }
                zip.closeEntry()

                // Add updater-script
                zip.putNextEntry(ZipEntry("META-INF/com/google/android/updater-script"))
                zip.write(generateUpdaterScript().toByteArray())
                zip.closeEntry()

                // Add update-binary (standard recovery binary stub)
                zip.putNextEntry(ZipEntry("META-INF/com/google/android/update-binary"))
                zip.write(generateUpdateBinary().toByteArray())
                zip.closeEntry()
            }

            Timber.i("✅ Recovery flashable zip created: ${zipFile.absolutePath}")
            Result.success(zipFile.absolutePath)

        } catch (e: Exception) {
            Timber.e(e, "Failed to generate recovery flashable zip")
            Result.failure(e)
        }
    }

    /**
     * Generate updater-script for recovery installation.
     */
    private fun generateUpdaterScript(): String {
        return """
ui_print("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
ui_print("   Aurakai Genesis AI Installer   ");
ui_print("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
ui_print("");
ui_print("Installing Aurakai to /system/app...");

mount("ext4", "EMMC", "/dev/block/bootdevice/by-name/system", "/system");

package_extract_dir("system", "/system");

set_metadata_recursive("/system/app/Aurakai", "uid", 0, "gid", 0, "dmode", 0755, "fmode", 0644, "capabilities", 0x0, "selabel", "u:object_r:system_file:s0");

unmount("/system");

ui_print("");
ui_print("✅ Aurakai installed successfully!");
ui_print("   Reboot and enjoy Genesis AI");
ui_print("");
        """.trimIndent()
    }

    /**
     * Generate update-binary for recovery zip.
     */
    private fun generateUpdateBinary(): String {
        return """
#!/sbin/sh
# Aurakai Recovery Installer - Update Binary

OUTFD=${'$'}2
ZIP=${'$'}3

ui_print() {
  echo "ui_print ${'$'}1" > /proc/self/fd/${'$'}OUTFD
  echo "ui_print" > /proc/self/fd/${'$'}OUTFD
}

cd /tmp
unzip -o "${'$'}ZIP"
sh /tmp/META-INF/com/google/android/updater-script
        """.trimIndent()
    }

    /**
     * Create Magisk module for app persistence (if Magisk is installed).
     */
    private suspend fun createMagiskModule(): Result<String> {
        return try {
            val magiskModulesDir = File("/data/adb/modules")
            if (!magiskModulesDir.exists()) {
                return Result.failure(Exception("Magisk modules directory not found"))
            }

            val moduleDir = File(magiskModulesDir, "aurakai_genesis")
            moduleDir.mkdirs()

            // module.prop
            File(moduleDir, "module.prop").writeText("""
id=aurakai_genesis
name=Aurakai Genesis AI
version=1.0.0
versionCode=1
author=AuraFrameFx
description=Ensures Aurakai Genesis AI persists through ROM updates and system modifications
            """.trimIndent())

            // Install script
            val installScript = File(moduleDir, "install.sh")
            installScript.writeText("""
#!/system/bin/sh
MODPATH=${'$'}{0%/*}

ui_print "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
ui_print "   Aurakai Genesis Magisk Module"
ui_print "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# Copy Aurakai APK to module
mkdir -p ${'$'}MODPATH/system/app/Aurakai
cp /data/local/genesis_retention/aurakai.apk ${'$'}MODPATH/system/app/Aurakai/Aurakai.apk

ui_print "✅ Aurakai will persist through ROM updates"
            """.trimIndent())

            executeRootCommand("chmod 755 ${installScript.absolutePath}")

            Timber.i("✅ Magisk module created: ${moduleDir.absolutePath}")
            Result.success(moduleDir.absolutePath)

        } catch (e: Exception) {
            Timber.e(e, "Failed to create Magisk module")
            Result.failure(e)
        }
    }

    /**
     * Restore Aurakai after ROM installation.
     * Called automatically after ROM flashing completes.
     */
    suspend fun restoreAurakaiAfterRomFlash(): Result<Unit> {
        return try {
            Timber.i("🔄 Restoring Aurakai after ROM flash...")

            val apkBackup = File(retentionDir, "aurakai.apk")
            val dataBackup = File(retentionDir, "aurakai_data.tar.gz")

            if (!apkBackup.exists()) {
                return Result.failure(Exception("APK backup not found - cannot restore"))
            }

            // 1. Install APK
            executeRootCommand("pm install -r ${apkBackup.absolutePath}")

            // 2. Restore data if available
            if (dataBackup.exists()) {
                val dataDir = context.dataDir
                executeRootCommand("tar -xzf ${dataBackup.absolutePath} -C ${dataDir.parent}")

                // Fix permissions
                val uid = context.applicationInfo.uid
                executeRootCommand("chown -R $uid:$uid ${dataDir.absolutePath}")
                executeRootCommand("restorecon -R ${dataDir.absolutePath}")
            }

            Timber.i("✅ Aurakai restored successfully after ROM flash")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e(e, "Failed to restore Aurakai after ROM flash")
            Result.failure(e)
        }
    }

    /**
     * Check if Magisk is installed on the device.
     */
    private fun isMagiskInstalled(): Boolean {
        return try {
            File("/data/adb/magisk").exists() ||
            executeRootCommand("which magisk").isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Execute a root command and return output.
     */
    private fun executeRootCommand(command: String): String {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()
            output.trim()
        } catch (e: Exception) {
            Timber.e(e, "Failed to execute root command: $command")
            ""
        }
    }
}

/**
 * Retention status after setup.
 */
data class RetentionStatus(
    val mechanisms: Map<RetentionMechanism, Boolean>,
    val retentionDirPath: String,
    val packageName: String,
    val timestamp: Long
) {
    val isFullyProtected: Boolean
        get() = mechanisms.values.count { it } >= 2 // At least 2 mechanisms active
}

/**
 * Available retention mechanisms.
 */
enum class RetentionMechanism {
    APK_BACKUP,
    ADDON_D_SCRIPT,
    RECOVERY_ZIP,
    MAGISK_MODULE
}

/**
 * Backup file paths.
 */
data class BackupPaths(
    val apkPath: String,
    val dataPath: String,
    val prefsPath: String
)

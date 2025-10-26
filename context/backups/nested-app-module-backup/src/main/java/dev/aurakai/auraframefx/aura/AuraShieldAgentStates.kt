package dev.aurakai.auraframefx.model.agent_states

/**
 * Security context state for Aura Shield Agent.
 * Tracks device security status, root detection, and SELinux configuration.
 */
data class SecurityContextState(
    val deviceRooted: Boolean = false,
    val selinuxMode: String = "Unknown", // "Enforcing", "Permissive", "Disabled", "Unknown"
    val harmfulAppScore: Float = 0.0f, // 0.0 = safe, 1.0 = critical
    val lastScanTime: Long? = null,
    val knownThreats: Int = 0
)

/**
 * Active security threat detected by Aura Shield.
 */
data class ActiveThreat(
    val threatId: String,
    val description: String,
    val severity: ThreatSeverity,
    val recommendedAction: String? = null,
    val detectedAt: Long = System.currentTimeMillis(),
    val threatType: ThreatType = ThreatType.UNKNOWN
)

/**
 * Security scan event logged by Aura Shield.
 */
data class ScanEvent(
    val eventId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val scanType: ScanType,
    val findings: List<String> = emptyList(),
    val duration: Long = 0L, // milliseconds
    val scannedItems: Int = 0
)

enum class ThreatSeverity(val level: Int) {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4)
}

enum class ThreatType {
    MALWARE,
    SUSPICIOUS_APP,
    ROOT_ACCESS,
    PERMISSION_ABUSE,
    NETWORK_THREAT,
    UNKNOWN
}

enum class ScanType {
    APP_SCAN,
    FILE_SYSTEM_SCAN,
    NETWORK_SCAN,
    QUICK_SCAN,
    FULL_SCAN
}

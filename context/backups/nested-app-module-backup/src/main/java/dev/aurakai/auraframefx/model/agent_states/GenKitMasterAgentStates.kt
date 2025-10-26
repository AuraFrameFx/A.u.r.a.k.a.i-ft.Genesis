package dev.aurakai.auraframefx.model.agent_states

/**
 * UI state for GenKit Master Agent.
 * Manages the overall system status and agent coordination.
 */
data class GenKitUiState(
    val systemStatus: SystemStatus = SystemStatus.NOMINAL,
    val activeAgentCount: Int = 0,
    val lastOptimizationTime: Long? = null,
    val totalOperations: Long = 0L,
    val averageResponseTime: Long = 0L, // milliseconds
    val systemLoad: Float = 0.0f // 0.0 = idle, 1.0 = full load
)

enum class SystemStatus {
    NOMINAL,
    INITIALIZING,
    DEGRADED,
    ERROR,
    MAINTENANCE,
    SHUTDOWN
}

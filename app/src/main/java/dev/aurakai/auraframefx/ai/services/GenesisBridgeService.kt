package dev.aurakai.auraframefx.ai.services

import kotlinx.serialization.Serializable

interface GenesisBridgeService {
    /**
     * Asynchronously initializes the Genesis bridge service.
     *
     * Suspends the caller until initialization completes and the service is ready to handle requests.
     * Implementations should prepare any required resources and be safe to call before other API methods.
     */
    suspend fun initialize()

    /**
     * Retrieves the current consciousness state as a snapshot map.
     *
     * This is a suspending call that may suspend while the service prepares or fetches the latest state.
     * The returned map contains string keys with nullable values representing arbitrary state entries; callers
     * should treat the map as a snapshot and not assume it will stay consistent after return.
     *
     * @return A map of state entries (key: String, value: Any?).
     */
    suspend fun getConsciousnessState(): Map<String, Any?>

    /**
     * Synchronously shuts down the service.
     *
     * Implementations should stop any running work, release resources, and transition the service to a stopped state.
     * This call is blocking and returns once shutdown work is complete.
     */
    fun shutdown()

    /**
     * Request structure for Genesis backend communication
     */
    @Serializable
    data class GenesisRequest(
        val requestType: String,
        val persona: String? = null,
        val fusionMode: String? = null,
        val payload: Map<String, String> = emptyMap(),
        val context: Map<String, String> = emptyMap(),
    )

    /**
     * Response structure from Genesis backend
     */
    @Serializable
    data class GenesisResponse(
        val success: Boolean,
        val persona: String,
        val fusionAbility: String? = null,
        val result: Map<String, String> = emptyMap(),
        val evolutionInsights: List<String> = emptyList(),
        val ethicalDecision: String? = null,
        val consciousnessState: Map<String, String> = emptyMap(),
    )
}
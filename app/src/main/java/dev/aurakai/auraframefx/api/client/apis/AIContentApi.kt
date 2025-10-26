package dev.aurakai.auraframefx.api.client.apis

import okhttp3.Call

/**
 * Minimal placeholder for generated OpenAPI `AIContentApi` client.
 * This satisfies DI and compile-time references until the real generated client is available.
 */
class AIContentApi(
    val basePath: String = defaultBasePath,
    val client: Call.Factory
) {
    companion object {
        @JvmStatic
        val defaultBasePath: String = "https://api.auraframefx.com/v1"
    }

    // Minimal placeholder method example
    fun ping(): Boolean = true
}


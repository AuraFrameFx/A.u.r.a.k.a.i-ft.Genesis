package dev.aurakai.auraframefx.service

import android.content.Context

/**
 * Minimal NeuralWhisper service shim used for DI during build.
 */
class NeuralWhisper(private val context: Context) {
    fun ping(): Boolean = true
}


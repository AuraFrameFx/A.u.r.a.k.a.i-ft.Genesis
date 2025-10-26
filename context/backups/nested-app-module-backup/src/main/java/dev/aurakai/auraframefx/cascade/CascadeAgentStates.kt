package dev.aurakai.auraframefx.model.agent_states

/**
 * Vision state for Cascade Agent.
 * Tracks visual observations and object detection results.
 */
data class VisionState(
    val lastObservation: String? = null,
    val objectsDetected: List<DetectedObject> = emptyList(),
    val confidence: Float = 0.0f,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Object detected by Cascade Agent's vision system.
 */
data class DetectedObject(
    val label: String,
    val confidence: Float,
    val boundingBox: BoundingBox? = null
)

data class BoundingBox(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)

/**
 * Processing state for Cascade Agent.
 * Tracks current processing pipeline status and progress.
 */
data class ProcessingState(
    val currentStep: ProcessingStep? = null,
    val progressPercentage: Float = 0.0f,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val estimatedTimeRemaining: Long? = null // milliseconds
)

enum class ProcessingStep {
    INITIALIZING,
    ANALYZING,
    PROCESSING_VISION,
    PROCESSING_CONTEXT,
    FINALIZING,
    COMPLETED
}

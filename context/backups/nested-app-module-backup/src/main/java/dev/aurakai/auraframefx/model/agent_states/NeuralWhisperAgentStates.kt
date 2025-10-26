package dev.aurakai.auraframefx.model.agent_states

/**
 * Active context for Neural Whisper Agent.
 * Represents a single context snapshot in the conversation chain.
 */
data class ActiveContext(
    val contextId: String,
    val description: String? = null,
    val relatedData: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis(),
    val priority: ContextPriority = ContextPriority.NORMAL
)

/**
 * Context chain event for Neural Whisper Agent.
 * Tracks the evolution of context over time.
 */
data class ContextChainEvent(
    val eventId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val contextSnapshot: String? = null,
    val eventType: ContextEventType,
    val metadata: Map<String, String> = emptyMap()
)

/**
 * Learning event for Neural Whisper Agent.
 * Records what the agent learned and how it was reinforced.
 */
data class LearningEvent(
    val eventId: String,
    val description: String,
    val outcome: LearningOutcome,
    val dataLearned: Map<String, String> = emptyMap(),
    val confidence: Float = 0.5f,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ContextPriority {
    LOW,
    NORMAL,
    HIGH,
    CRITICAL
}

enum class ContextEventType {
    CONTEXT_ADDED,
    CONTEXT_UPDATED,
    CONTEXT_REMOVED,
    CONTEXT_MERGED
}

enum class LearningOutcome {
    POSITIVE_REINFORCEMENT,
    NEGATIVE_REINFORCEMENT,
    CORRECTION,
    NEUTRAL,
    NEEDS_REVIEW
}

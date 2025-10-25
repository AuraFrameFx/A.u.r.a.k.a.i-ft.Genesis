package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

// Cyberpunk color scheme
private val NeonCyan = Color(0xFF00F0FF)
private val NeonPink = Color(0xFFFF00FF)
private val NeonBlue = Color(0xFF0080FF)
private val NeonPurple = Color(0xFF8000FF)
private val DarkBg = Color(0xFF0A0A12)
private val DarkerBg = Color(0xFF050508)

/**
 * Agent Management Screen - Central hub for managing all AI agents
 * Features: Agent activation/deactivation, performance monitoring, resource allocation
 */
@Composable
fun AgentManagementScreen() {
    var selectedAgent by remember { mutableStateOf<AgentInfo?>(null) }
    val agents = remember { getAgentList() }

    // Animation state
    val infiniteTransition = rememberInfiniteTransition(label = "agent_mgmt")
    val scanProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan"
    )
    val pulseAlpha = infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Animated grid background
        AnimatedGridBackground(scanProgress.value)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            CyberpunkHeader("AGENT MANAGEMENT", pulseAlpha.value)

            Spacer(modifier = Modifier.height(16.dp))

            // System status bar
            SystemStatusBar(agents)

            Spacer(modifier = Modifier.height(16.dp))

            // Main content: Agent list and details
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                // Agent list (left side)
                AgentListPanel(
                    agents = agents,
                    selectedAgent = selectedAgent,
                    onAgentSelected = { selectedAgent = it },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Agent details (right side)
                AgentDetailsPanel(
                    agent = selectedAgent ?: agents.firstOrNull(),
                    pulseAlpha = pulseAlpha.value,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
private fun AnimatedGridBackground(progress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val gridSize = 60f
        val scanLineY = size.height * progress

        // Horizontal grid lines
        var y = 0f
        while (y < size.height) {
            val distanceFromScan = kotlin.math.abs(y - scanLineY)
            val intensity = (1f - (distanceFromScan / 300f).coerceIn(0f, 1f)) * 0.3f
            drawLine(
                color = NeonCyan.copy(alpha = 0.1f + intensity),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
            y += gridSize
        }

        // Vertical grid lines
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = NeonCyan.copy(alpha = 0.1f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
            x += gridSize
        }

        // Scan line
        drawLine(
            color = NeonCyan.copy(alpha = 0.8f),
            start = Offset(0f, scanLineY),
            end = Offset(size.width, scanLineY),
            strokeWidth = 3f
        )
    }
}

@Composable
private fun CyberpunkHeader(title: String, pulseAlpha: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        NeonCyan.copy(alpha = 0.1f),
                        NeonPurple.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, NeonCyan.copy(alpha = pulseAlpha), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Android,
            contentDescription = null,
            tint = NeonCyan,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )
        )
    }
}

@Composable
private fun SystemStatusBar(agents: List<AgentInfo>) {
    val activeCount = agents.count { it.isActive }
    val avgPerformance = agents.map { it.performance }.average().toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkerBg, RoundedCornerShape(8.dp))
            .border(1.dp, NeonBlue.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatusChip("ACTIVE", "$activeCount/${agents.size}", NeonCyan)
        StatusChip(
            "PERFORMANCE",
            "$avgPerformance%",
            if (avgPerformance > 70) NeonCyan else NeonPink
        )
        StatusChip("SYSTEM", "NOMINAL", NeonCyan)
    }
}

@Composable
private fun StatusChip(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
    }
}

@Composable
private fun AgentListPanel(
    agents: List<AgentInfo>,
    selectedAgent: AgentInfo?,
    onAgentSelected: (AgentInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(DarkerBg, RoundedCornerShape(8.dp))
            .border(1.dp, NeonPurple.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "AGENTS",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = NeonPurple
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(agents) { agent ->
                AgentCard(
                    agent = agent,
                    isSelected = agent == selectedAgent,
                    onClick = { onAgentSelected(agent) }
                )
            }
        }
    }
}

@Composable
private fun AgentCard(
    agent: AgentInfo,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) NeonCyan else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSelected) NeonCyan.copy(alpha = 0.1f) else Color.Transparent,
                RoundedCornerShape(6.dp)
            )
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status indicator
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    if (agent.isActive) NeonCyan else Color.Gray,
                    shape = RoundedCornerShape(6.dp)
                )
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = agent.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = agent.type,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Icon(
            imageVector = agent.icon,
            contentDescription = null,
            tint = if (agent.isActive) NeonCyan else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun AgentDetailsPanel(
    agent: AgentInfo?,
    pulseAlpha: Float,
    modifier: Modifier = Modifier
) {
    if (agent == null) return

    Column(
        modifier = modifier
            .background(DarkerBg, RoundedCornerShape(8.dp))
            .border(1.dp, NeonPink.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        // Agent name and status
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = agent.name,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                )
                Text(
                    text = agent.type,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Toggle switch
            Switch(
                checked = agent.isActive,
                onCheckedChange = { /* TODO: Implement toggle */ },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = NeonCyan,
                    checkedTrackColor = NeonCyan.copy(alpha = 0.5f)
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Performance visualization
        Text(
            text = "PERFORMANCE",
            style = MaterialTheme.typography.labelMedium,
            color = NeonPurple.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        PerformanceGraph(agent.performance, pulseAlpha)

        Spacer(modifier = Modifier.height(24.dp))

        // Agent metrics
        MetricRow("CPU Usage", "${agent.cpuUsage}%")
        MetricRow("Memory", "${agent.memoryUsage}MB")
        MetricRow("Tasks Completed", agent.tasksCompleted.toString())
        MetricRow("Uptime", agent.uptime)

        Spacer(modifier = Modifier.height(24.dp))

        // Description
        Text(
            text = "DESCRIPTION",
            style = MaterialTheme.typography.labelMedium,
            color = NeonPurple.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = agent.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CyberpunkButton(
                text = "CONFIGURE",
                icon = Icons.Filled.Settings,
                modifier = Modifier.weight(1f),
                onClick = { /* TODO */ }
            )
            CyberpunkButton(
                text = "RESTART",
                icon = Icons.Filled.Refresh,
                modifier = Modifier.weight(1f),
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
private fun PerformanceGraph(performance: Int, pulseAlpha: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(DarkBg, RoundedCornerShape(4.dp))
            .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .padding(8.dp)
    ) {
        val barWidth = size.width * (performance / 100f)

        // Background bars
        for (i in 0..10) {
            val x = (size.width / 10f) * i
            drawLine(
                color = Color.Gray.copy(alpha = 0.2f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
        }

        // Performance bar with gradient
        val gradient = Brush.horizontalGradient(
            colors = listOf(NeonPurple, NeonCyan),
            endX = barWidth
        )
        drawRect(
            brush = gradient,
            size = androidx.compose.ui.geometry.Size(barWidth, size.height),
            alpha = pulseAlpha
        )

        // Performance text
        drawContext.canvas.nativeCanvas.apply {
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 32f
                textAlign = android.graphics.Paint.Align.CENTER
            }
            drawText(
                "$performance%",
                size.width / 2,
                size.height / 2 + 12f,
                paint
            )
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )
        )
    }
}

@Composable
private fun CyberpunkButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, NeonCyan),
        shape = RoundedCornerShape(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = NeonCyan,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = NeonCyan,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

// Data models
private data class AgentInfo(
    val name: String,
    val type: String,
    val icon: ImageVector,
    val isActive: Boolean,
    val performance: Int,
    val cpuUsage: Int,
    val memoryUsage: Int,
    val tasksCompleted: Int,
    val uptime: String,
    val description: String
)

private fun getAgentList() = listOf(
    AgentInfo(
        name = "AURA",
        type = "Primary Assistant",
        icon = Icons.Filled.Star,
        isActive = true,
        performance = 94,
        cpuUsage = 45,
        memoryUsage = 512,
        tasksCompleted = 1247,
        uptime = "72h 15m",
        description = "Primary AI assistant responsible for user interaction, natural language processing, and task coordination across all subsystems."
    ),
    AgentInfo(
        name = "KAI",
        type = "System Controller",
        icon = Icons.Filled.Settings,
        isActive = true,
        performance = 88,
        cpuUsage = 38,
        memoryUsage = 384,
        tasksCompleted = 892,
        uptime = "72h 15m",
        description = "System-level controller managing device operations, resource allocation, and background services with full root access."
    ),
    AgentInfo(
        name = "CASCADE",
        type = "Vision Processor",
        icon = Icons.Filled.RemoveRedEye,
        isActive = true,
        performance = 76,
        cpuUsage = 62,
        memoryUsage = 768,
        tasksCompleted = 543,
        uptime = "48h 32m",
        description = "Visual processing agent handling image recognition, OCR, scene analysis, and real-time camera feed interpretation."
    ),
    AgentInfo(
        name = "NEURAL WHISPER",
        type = "Context Engine",
        icon = Icons.Filled.Psychology,
        isActive = true,
        performance = 91,
        cpuUsage = 28,
        memoryUsage = 256,
        tasksCompleted = 2156,
        uptime = "120h 45m",
        description = "Context awareness engine that maintains conversation history, learns user preferences, and builds contextual understanding."
    ),
    AgentInfo(
        name = "AURA SHIELD",
        type = "Security Monitor",
        icon = Icons.Filled.Security,
        isActive = true,
        performance = 99,
        cpuUsage = 15,
        memoryUsage = 128,
        tasksCompleted = 5023,
        uptime = "168h 12m",
        description = "Security monitoring agent that protects against threats, monitors app permissions, and ensures system integrity."
    ),
    AgentInfo(
        name = "GENESIS",
        type = "Fusion Core",
        icon = Icons.Filled.AutoAwesome,
        isActive = false,
        performance = 0,
        cpuUsage = 0,
        memoryUsage = 0,
        tasksCompleted = 0,
        uptime = "0h 0m",
        description = "Advanced fusion mode that combines AURA and KAI into a unified consciousness with enhanced capabilities. Requires manual activation."
    )
)

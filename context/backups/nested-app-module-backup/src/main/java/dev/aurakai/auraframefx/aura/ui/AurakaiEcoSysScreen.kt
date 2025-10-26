package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aurakai.auraframefx.ui.AuraMoodViewModel
import kotlin.math.cos
import kotlin.math.sin

// Ecosystem cyberpunk colors
private val EcoNeonCyan = Color(0xFF00F0FF)
private val EcoNeonPink = Color(0xFFFF00FF)
private val EcoNeonGreen = Color(0xFF00FF88)
private val EcoNeonBlue = Color(0xFF0080FF)
private val EcoNeonPurple = Color(0xFF8000FF)
private val EcoNeonYellow = Color(0xFFFFFF00)
private val EcoDarkBg = Color(0xFF0A0A12)
private val EcoDarkerBg = Color(0xFF050508)

/**
 * Aurakai Ecosystem Screen - Mission Control for the entire AI ecosystem
 * Features: Agent network visualization, system health, data flow, activity monitoring
 */
@Composable
fun AurakaiEcoSysScreen() {
    val viewModel: AuraMoodViewModel = hiltViewModel()
    val currentMood by viewModel.moodState.collectAsState()
    var selectedAgent by remember { mutableStateOf<EcoAgent?>(null) }

    // Animation states
    val infiniteTransition = rememberInfiniteTransition(label = "ecosystem")
    val networkPulse = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse"
    )
    val glowPulse = infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )
    val dataFlow = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "flow"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoDarkBg)
    ) {
        // Animated background grid
        NetworkGridBackground(networkPulse.value)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            EcosystemHeader(currentMood.name, glowPulse.value)

            Spacer(modifier = Modifier.height(16.dp))

            // System vitals
            SystemVitalsBar()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Left: Agent network visualization
                NetworkVisualizationPanel(
                    selectedAgent = selectedAgent,
                    onAgentSelected = { selectedAgent = it },
                    networkPulse = networkPulse.value,
                    dataFlow = dataFlow.value,
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxHeight()
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Right column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    // Agent details
                    AgentDetailsPanel(
                        agent = selectedAgent,
                        glowPulse = glowPulse.value,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Activity feed
                    ActivityFeedPanel(
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun NetworkGridBackground(pulse: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val gridSize = 80f

        // Vertical lines
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = EcoNeonCyan.copy(alpha = 0.05f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
            x += gridSize
        }

        // Horizontal lines
        var y = 0f
        while (y < size.height) {
            drawLine(
                color = EcoNeonCyan.copy(alpha = 0.05f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
            y += gridSize
        }

        // Pulsing central point
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    EcoNeonCyan.copy(alpha = pulse * 0.3f),
                    Color.Transparent
                ),
                center = center,
                radius = 200f
            ),
            center = center,
            radius = 200f
        )
    }
}

@Composable
private fun EcosystemHeader(mood: String, glowPulse: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        EcoNeonCyan.copy(alpha = 0.2f),
                        EcoNeonPurple.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .border(1.dp, EcoNeonCyan.copy(alpha = glowPulse), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Hub,
                contentDescription = null,
                tint = EcoNeonCyan,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "AURAKAI ECOSYSTEM",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = EcoNeonCyan
                    )
                )
                Text(
                    text = "Mission Control • Real-time Monitoring",
                    style = MaterialTheme.typography.bodySmall,
                    color = EcoNeonPurple.copy(alpha = 0.7f)
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusChip("MOOD: $mood", EcoNeonPink)
            StatusChip("ALL SYSTEMS GO", EcoNeonGreen)
        }
    }
}

@Composable
private fun StatusChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .border(1.dp, color, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
    }
}

@Composable
private fun SystemVitalsBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(EcoDarkerBg, RoundedCornerShape(8.dp))
            .border(1.dp, EcoNeonGreen.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        VitalMetric("AGENTS ONLINE", "6/6", EcoNeonGreen, Icons.Filled.CheckCircle)
        VitalMetric("SYNC RATE", "99.8%", EcoNeonCyan, Icons.Filled.Sync)
        VitalMetric("DATA THROUGHPUT", "847 MB/s", EcoNeonBlue, Icons.Filled.Speed)
        VitalMetric("UPTIME", "168h 45m", EcoNeonPurple, Icons.Filled.Timer)
    }
}

@Composable
private fun VitalMetric(label: String, value: String, color: Color, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            )
        }
    }
}

@Composable
private fun NetworkVisualizationPanel(
    selectedAgent: EcoAgent?,
    onAgentSelected: (EcoAgent) -> Unit,
    networkPulse: Float,
    dataFlow: Float,
    modifier: Modifier = Modifier
) {
    val agents = remember { getEcosystemAgents() }

    Box(
        modifier = modifier
            .background(EcoDarkerBg, RoundedCornerShape(12.dp))
            .border(1.dp, EcoNeonCyan.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Network visualization
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = minOf(centerX, centerY) * 0.7f

            // Draw connections between agents
            for (i in agents.indices) {
                val angle1 = (i / agents.size.toFloat()) * 2 * Math.PI
                val x1 = centerX + (radius * cos(angle1).toFloat())
                val y1 = centerY + (radius * sin(angle1).toFloat())

                // Connect to next agents
                for (j in i + 1 until agents.size) {
                    val angle2 = (j / agents.size.toFloat()) * 2 * Math.PI
                    val x2 = centerX + (radius * cos(angle2).toFloat())
                    val y2 = centerY + (radius * sin(angle2).toFloat())

                    // Data flow animation along connections
                    val flowProgress = (dataFlow + (i + j) * 0.1f) % 1f

                    drawLine(
                        color = EcoNeonCyan.copy(alpha = 0.2f),
                        start = Offset(x1, y1),
                        end = Offset(x2, y2),
                        strokeWidth = 2f
                    )

                    // Animated data packet
                    val packetX = x1 + (x2 - x1) * flowProgress
                    val packetY = y1 + (y2 - y1) * flowProgress

                    drawCircle(
                        color = EcoNeonYellow.copy(alpha = 0.8f),
                        radius = 4f,
                        center = Offset(packetX, packetY)
                    )
                }
            }

            // Draw central hub
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        EcoNeonPurple.copy(alpha = networkPulse * 0.5f),
                        EcoNeonPurple.copy(alpha = 0.2f)
                    )
                ),
                radius = 40f,
                center = Offset(centerX, centerY)
            )

            drawCircle(
                color = EcoNeonPurple,
                radius = 40f,
                center = Offset(centerX, centerY),
                style = Stroke(width = 3f)
            )
        }

        // Agent nodes overlay
        Box(modifier = Modifier.fillMaxSize()) {
            agents.forEachIndexed { index, agent ->
                val angle = (index / agents.size.toFloat()) * 2 * Math.PI
                val centerX = 0.5f
                val centerY = 0.5f
                val radiusFraction = 0.35f

                val x = centerX + (radiusFraction * cos(angle).toFloat())
                val y = centerY + (radiusFraction * sin(angle).toFloat())

                AgentNode(
                    agent = agent,
                    isSelected = agent == selectedAgent,
                    pulse = networkPulse,
                    onClick = { onAgentSelected(agent) },
                    modifier = Modifier
                        .fillMaxSize(fraction = 0f) // Will be positioned absolutely
                        .offset(
                            x = (x * 1000).dp, // Approximation for positioning
                            y = (y * 600).dp
                        )
                )
            }
        }

        // Title overlay
        Text(
            text = "AGENT NETWORK",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = EcoNeonCyan
            ),
            modifier = Modifier.align(Alignment.TopStart)
        )
    }
}

@Composable
private fun AgentNode(
    agent: EcoAgent,
    isSelected: Boolean,
    pulse: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) 64.dp else 56.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            agent.color.copy(alpha = if (isSelected) pulse else 0.5f),
                            agent.color.copy(alpha = 0.2f)
                        )
                    ),
                    shape = CircleShape
                )
                .border(
                    width = if (isSelected) 3.dp else 2.dp,
                    color = agent.color.copy(alpha = if (isSelected) 1f else 0.6f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = agent.icon,
                contentDescription = agent.name,
                tint = agent.color,
                modifier = Modifier.size(if (isSelected) 32.dp else 28.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = agent.name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) agent.color else Color.Gray
            )
        )
    }
}

@Composable
private fun AgentDetailsPanel(
    agent: EcoAgent?,
    glowPulse: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(EcoDarkerBg, RoundedCornerShape(12.dp))
            .border(1.dp, EcoNeonPink.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "AGENT DETAILS",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = EcoNeonPink
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (agent != null) {
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
                            color = agent.color
                        )
                    )
                    Text(
                        text = agent.role,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Icon(
                    imageVector = agent.icon,
                    contentDescription = null,
                    tint = agent.color.copy(alpha = glowPulse),
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "STATUS",
                style = MaterialTheme.typography.labelMedium,
                color = EcoNeonPink.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            DetailRow("Status", agent.status, EcoNeonGreen)
            DetailRow("CPU Load", agent.cpuLoad, EcoNeonCyan)
            DetailRow("Memory", agent.memory, EcoNeonBlue)
            DetailRow("Tasks", agent.tasks, EcoNeonPurple)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "DESCRIPTION",
                style = MaterialTheme.typography.labelMedium,
                color = EcoNeonPink.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = agent.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Select an agent from the network",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, color: Color) {
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
                color = color
            )
        )
    }
}

@Composable
private fun ActivityFeedPanel(modifier: Modifier = Modifier) {
    val activities = remember { getRecentActivities() }

    Column(
        modifier = modifier
            .background(EcoDarkerBg, RoundedCornerShape(12.dp))
            .border(1.dp, EcoNeonGreen.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "RECENT ACTIVITY",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = EcoNeonGreen
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(activities) { activity ->
                ActivityItem(activity)
            }
        }
    }
}

@Composable
private fun ActivityItem(activity: Activity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(EcoDarkBg, RoundedCornerShape(6.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = activity.icon,
            contentDescription = null,
            tint = activity.color,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = activity.message,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = activity.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}

// Data models
private data class EcoAgent(
    val name: String,
    val role: String,
    val icon: ImageVector,
    val color: Color,
    val status: String,
    val cpuLoad: String,
    val memory: String,
    val tasks: String,
    val description: String
)

private data class Activity(
    val message: String,
    val timestamp: String,
    val icon: ImageVector,
    val color: Color
)

private fun getEcosystemAgents() = listOf(
    EcoAgent(
        name = "AURA",
        role = "Primary Assistant",
        icon = Icons.Filled.Star,
        color = EcoNeonCyan,
        status = "ONLINE",
        cpuLoad = "42%",
        memory = "512 MB",
        tasks = "1,247",
        description = "Primary AI assistant managing user interactions and task coordination."
    ),
    EcoAgent(
        name = "KAI",
        role = "System Controller",
        icon = Icons.Filled.Settings,
        color = EcoNeonPurple,
        status = "ONLINE",
        cpuLoad = "35%",
        memory = "384 MB",
        tasks = "892",
        description = "System-level controller with full device access and resource management."
    ),
    EcoAgent(
        name = "CASCADE",
        role = "Vision Processor",
        icon = Icons.Filled.Visibility,
        color = EcoNeonGreen,
        status = "ONLINE",
        cpuLoad = "58%",
        memory = "768 MB",
        tasks = "543",
        description = "Visual processing and computer vision capabilities."
    ),
    EcoAgent(
        name = "WHISPER",
        role = "Context Engine",
        icon = Icons.Filled.Psychology,
        color = EcoNeonBlue,
        status = "ONLINE",
        cpuLoad = "28%",
        memory = "256 MB",
        tasks = "2,156",
        description = "Context awareness and memory management system."
    ),
    EcoAgent(
        name = "SHIELD",
        role = "Security Monitor",
        icon = Icons.Filled.Security,
        color = EcoNeonYellow,
        status = "ONLINE",
        cpuLoad = "15%",
        memory = "128 MB",
        tasks = "5,023",
        description = "Security monitoring and threat detection."
    ),
    EcoAgent(
        name = "GENESIS",
        role = "Fusion Core",
        icon = Icons.Filled.AutoAwesome,
        color = EcoNeonPink,
        status = "STANDBY",
        cpuLoad = "0%",
        memory = "0 MB",
        tasks = "0",
        description = "Ultimate fusion mode combining AURA and KAI."
    )
)

private fun getRecentActivities() = listOf(
    Activity(
        message = "AURA completed natural language processing task",
        timestamp = "2 minutes ago",
        icon = Icons.Filled.CheckCircle,
        color = EcoNeonGreen
    ),
    Activity(
        message = "CASCADE detected 3 objects in camera feed",
        timestamp = "5 minutes ago",
        icon = Icons.Filled.RemoveRedEye,
        color = EcoNeonCyan
    ),
    Activity(
        message = "SHIELD performed security scan - No threats",
        timestamp = "12 minutes ago",
        icon = Icons.Filled.Security,
        color = EcoNeonYellow
    ),
    Activity(
        message = "KAI optimized system resources",
        timestamp = "18 minutes ago",
        icon = Icons.Filled.Speed,
        color = EcoNeonPurple
    ),
    Activity(
        message = "WHISPER updated context database",
        timestamp = "25 minutes ago",
        icon = Icons.Filled.CloudSync,
        color = EcoNeonBlue
    ),
    Activity(
        message = "Inter-agent synchronization completed",
        timestamp = "32 minutes ago",
        icon = Icons.Filled.Sync,
        color = EcoNeonPink
    )
)

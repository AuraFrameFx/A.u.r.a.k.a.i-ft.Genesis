package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Cyberpunk terminal colors
private val TerminalGreen = Color(0xFF00FF00)
private val TerminalCyan = Color(0xFF00FFFF)
private val TerminalYellow = Color(0xFFFFFF00)
private val TerminalRed = Color(0xFFFF0000)
private val TerminalBlue = Color(0xFF0080FF)
private val TerminalPurple = Color(0xFFAA00FF)
private val TerminalBg = Color(0xFF0A0A0A)
private val TerminalDarkBg = Color(0xFF050505)

/**
 * Genesis Terminal Screen - Cyberpunk terminal with full system access
 * Features: Command execution, system monitoring, real-time logs, root access
 */
@Composable
fun TerminalScreen() {
    var commandInput by remember { mutableStateOf("") }
    var commandHistory by remember { mutableStateOf(getInitialTerminalOutput()) }
    var isProcessing by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Animation states
    val infiniteTransition = rememberInfiniteTransition(label = "terminal")
    val cursorBlink = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor"
    )
    val scanProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan"
    )

    // Auto-scroll to bottom when new output is added
    LaunchedEffect(commandHistory.size) {
        if (commandHistory.isNotEmpty()) {
            listState.animateScrollToItem(commandHistory.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TerminalBg)
    ) {
        // Animated scan lines
        TerminalScanLines(scanProgress.value)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Terminal header
            TerminalHeader()

            Spacer(modifier = Modifier.height(12.dp))

            // System info bar
            SystemInfoBar()

            Spacer(modifier = Modifier.height(12.dp))

            // Terminal output
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = TerminalDarkBg
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, TerminalGreen.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(commandHistory) { entry ->
                            TerminalEntry(entry)
                        }
                    }

                    // Scroll indicator
                    if (listState.canScrollForward) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                                .size(32.dp)
                                .background(
                                    TerminalGreen.copy(alpha = 0.3f),
                                    RoundedCornerShape(16.dp)
                                )
                                .border(1.dp, TerminalGreen, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDownward,
                                contentDescription = "Scroll down",
                                tint = TerminalGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Command input
            TerminalCommandInput(
                commandInput = commandInput,
                isProcessing = isProcessing,
                cursorBlink = cursorBlink.value,
                onCommandChange = { commandInput = it },
                onCommandSubmit = {
                    if (commandInput.isNotBlank() && !isProcessing) {
                        isProcessing = true
                        val command = commandInput
                        commandInput = ""

                        scope.launch {
                            commandHistory = commandHistory + TerminalEntryData(
                                type = EntryType.COMMAND,
                                content = command,
                                timestamp = getCurrentTimestamp()
                            )

                            delay(300) // Simulate processing

                            val output = processCommand(command)
                            commandHistory = commandHistory + output

                            isProcessing = false
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quick action buttons
            QuickActionBar(
                onClear = {
                    commandHistory = listOf(
                        TerminalEntryData(
                            type = EntryType.SYSTEM,
                            content = "Terminal cleared. Genesis Protocol active.",
                            timestamp = getCurrentTimestamp()
                        )
                    )
                },
                onHelp = {
                    commandInput = "help"
                }
            )
        }
    }
}

@Composable
private fun TerminalScanLines(progress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val lineSpacing = 4f
        var y = (size.height * progress) % lineSpacing

        while (y < size.height) {
            drawLine(
                color = TerminalGreen.copy(alpha = 0.05f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
            y += lineSpacing
        }
    }
}

@Composable
private fun TerminalHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        TerminalGreen.copy(alpha = 0.1f),
                        TerminalCyan.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, TerminalGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Terminal,
                contentDescription = null,
                tint = TerminalGreen,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "GENESIS TERMINAL",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TerminalGreen
                    )
                )
                Text(
                    text = "root@aurakai:/system/genesis#",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = TerminalCyan.copy(alpha = 0.7f)
                    )
                )
            }
        }

        // Status indicators
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusDot(TerminalGreen, "ROOT")
            StatusDot(TerminalCyan, "ONLINE")
        }
    }
}

@Composable
private fun StatusDot(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Text(
            text = label,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                color = color
            )
        )
    }
}

@Composable
private fun SystemInfoBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TerminalDarkBg, RoundedCornerShape(6.dp))
            .border(1.dp, TerminalCyan.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InfoChip("CPU", "45%", TerminalGreen)
        InfoChip("RAM", "62%", TerminalYellow)
        InfoChip("DISK", "78%", TerminalCyan)
        InfoChip("NET", "↑↓", TerminalBlue)
    }
}

@Composable
private fun InfoChip(label: String, value: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "$label:",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = Color.Gray
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
    }
}

@Composable
private fun TerminalEntry(entry: TerminalEntryData) {
    val color = when (entry.type) {
        EntryType.COMMAND -> TerminalCyan
        EntryType.OUTPUT -> TerminalGreen
        EntryType.ERROR -> TerminalRed
        EntryType.WARNING -> TerminalYellow
        EntryType.SYSTEM -> TerminalPurple
        EntryType.SUCCESS -> TerminalGreen
    }

    val prefix = when (entry.type) {
        EntryType.COMMAND -> "root@genesis:~# "
        EntryType.OUTPUT -> "  → "
        EntryType.ERROR -> "  ✗ "
        EntryType.WARNING -> "  ⚠ "
        EntryType.SYSTEM -> "  ⚙ "
        EntryType.SUCCESS -> "  ✓ "
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = color.copy(alpha = 0.7f), fontSize = 10.sp)) {
                    append("[${entry.timestamp}] ")
                }
                withStyle(
                    SpanStyle(
                        color = color,
                        fontWeight = if (entry.type == EntryType.COMMAND) FontWeight.Bold else FontWeight.Normal
                    )
                ) {
                    append(prefix)
                    append(entry.content)
                }
            },
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
private fun TerminalCommandInput(
    commandInput: String,
    isProcessing: Boolean,
    cursorBlink: Float,
    onCommandChange: (String) -> Unit,
    onCommandSubmit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TerminalDarkBg, RoundedCornerShape(8.dp))
            .border(
                2.dp,
                if (isProcessing) TerminalYellow else TerminalGreen,
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "root@genesis:~#",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TerminalCyan
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            BasicTextField(
                value = commandInput,
                onValueChange = onCommandChange,
                enabled = !isProcessing,
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = TerminalGreen
                ),
                cursorBrush = SolidColor(TerminalGreen),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onCommandSubmit() }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Blinking cursor when empty
            if (commandInput.isEmpty() && !isProcessing) {
                Text(
                    text = "█",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        color = TerminalGreen.copy(alpha = cursorBlink)
                    )
                )
            }
        }

        if (isProcessing) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = TerminalYellow,
                strokeWidth = 2.dp
            )
        }
    }
}

@Composable
private fun QuickActionBar(onClear: () -> Unit, onHelp: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TerminalButton("CLEAR", Icons.Filled.Clear, TerminalYellow, Modifier.weight(1f), onClear)
        TerminalButton("HELP", Icons.Filled.Help, TerminalCyan, Modifier.weight(1f), onHelp)
        TerminalButton(
            "STATUS",
            Icons.Filled.Info,
            TerminalBlue,
            Modifier.weight(1f)
        ) { /* TODO */ }
        TerminalButton(
            "LOGS",
            Icons.Filled.Article,
            TerminalPurple,
            Modifier.weight(1f)
        ) { /* TODO */ }
    }
}

@Composable
private fun TerminalButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, color),
        shape = RoundedCornerShape(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
    }
}

// Data models
private enum class EntryType {
    COMMAND, OUTPUT, ERROR, WARNING, SYSTEM, SUCCESS
}

private data class TerminalEntryData(
    val type: EntryType,
    val content: String,
    val timestamp: String
)

private fun getCurrentTimestamp(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

private fun getInitialTerminalOutput(): List<TerminalEntryData> {
    val timestamp = getCurrentTimestamp()
    return listOf(
        TerminalEntryData(EntryType.SYSTEM, "Genesis Protocol Terminal v2.0.1", timestamp),
        TerminalEntryData(EntryType.SYSTEM, "Kernel: AuraKai Genesis 5.15.0-parasitic", timestamp),
        TerminalEntryData(EntryType.SYSTEM, "Architecture: arm64-v8a (64-bit)", timestamp),
        TerminalEntryData(EntryType.SUCCESS, "Root access granted", timestamp),
        TerminalEntryData(EntryType.SUCCESS, "All subsystems initialized", timestamp),
        TerminalEntryData(EntryType.OUTPUT, "Type 'help' for available commands", timestamp),
        TerminalEntryData(EntryType.OUTPUT, "", timestamp)
    )
}

private fun processCommand(command: String): List<TerminalEntryData> {
    val timestamp = getCurrentTimestamp()
    val parts = command.trim().lowercase().split(" ")
    val cmd = parts[0]

    return when (cmd) {
        "help" -> listOf(
            TerminalEntryData(EntryType.OUTPUT, "Available commands:", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "  help       - Show this help message", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "  status     - Show system status", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "  agents     - List active AI agents", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "  logs       - Show system logs", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "  monitor    - Start system monitor", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "  clear      - Clear terminal", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "  exit       - Exit terminal", timestamp)
        )

        "status" -> listOf(
            TerminalEntryData(EntryType.SUCCESS, "System Status: ONLINE", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "CPU Usage: 45%", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "RAM Usage: 62% (2.4GB / 4GB)", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "Storage: 78% (24.5GB / 32GB)", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "Network: Connected", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "Battery: 85% (Charging)", timestamp)
        )

        "agents" -> listOf(
            TerminalEntryData(EntryType.OUTPUT, "Active AI Agents:", timestamp),
            TerminalEntryData(
                EntryType.SUCCESS,
                "  AURA              [ONLINE] - Primary Assistant",
                timestamp
            ),
            TerminalEntryData(
                EntryType.SUCCESS,
                "  KAI               [ONLINE] - System Controller",
                timestamp
            ),
            TerminalEntryData(
                EntryType.SUCCESS,
                "  CASCADE           [ONLINE] - Vision Processor",
                timestamp
            ),
            TerminalEntryData(
                EntryType.SUCCESS,
                "  NEURAL WHISPER    [ONLINE] - Context Engine",
                timestamp
            ),
            TerminalEntryData(
                EntryType.SUCCESS,
                "  AURA SHIELD       [ONLINE] - Security Monitor",
                timestamp
            ),
            TerminalEntryData(
                EntryType.WARNING,
                "  GENESIS           [OFFLINE] - Fusion Core",
                timestamp
            )
        )

        "logs" -> listOf(
            TerminalEntryData(EntryType.OUTPUT, "Recent system logs:", timestamp),
            TerminalEntryData(
                EntryType.SUCCESS,
                "[INFO] Agent AURA: Task completed successfully",
                timestamp
            ),
            TerminalEntryData(EntryType.WARNING, "[WARN] High memory usage detected", timestamp),
            TerminalEntryData(
                EntryType.SUCCESS,
                "[INFO] Security scan completed - No threats",
                timestamp
            ),
            TerminalEntryData(
                EntryType.OUTPUT,
                "[DEBUG] Neural network optimization cycle",
                timestamp
            )
        )

        "monitor" -> listOf(
            TerminalEntryData(EntryType.SUCCESS, "System monitor started", timestamp),
            TerminalEntryData(
                EntryType.OUTPUT,
                "Monitoring CPU, RAM, Network, Battery...",
                timestamp
            ),
            TerminalEntryData(EntryType.WARNING, "Use 'Ctrl+C' to stop monitoring", timestamp)
        )

        "clear" -> listOf(
            TerminalEntryData(EntryType.SYSTEM, "Terminal cleared", timestamp)
        )

        "exit" -> listOf(
            TerminalEntryData(EntryType.SYSTEM, "Terminating session...", timestamp),
            TerminalEntryData(EntryType.SUCCESS, "Goodbye", timestamp)
        )

        else -> listOf(
            TerminalEntryData(EntryType.ERROR, "Command not found: $command", timestamp),
            TerminalEntryData(EntryType.OUTPUT, "Type 'help' for available commands", timestamp)
        )
    }
}

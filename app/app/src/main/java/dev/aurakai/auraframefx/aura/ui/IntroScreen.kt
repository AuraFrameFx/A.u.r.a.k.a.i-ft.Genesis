package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.theme.NeonBlue
import dev.aurakai.auraframefx.ui.theme.NeonCyan
import dev.aurakai.auraframefx.ui.theme.NeonPink
import dev.aurakai.auraframefx.ui.theme.NeonPurple
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

/**
 * Genesis Protocol Intro Screen
 *
 * Cyberpunk-themed introduction with digital tunnel effect - creates the illusion
 * of being pulled into a digital vortex with particles, scan lines, and glitch effects.
 */
@Composable
fun IntroScreen(
    onContinue: () -> Unit = {}
) {
    var showContent by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }

    // Trigger animations sequentially
    LaunchedEffect(Unit) {
        delay(500)
        showTitle = true
        delay(800)
        showSubtitle = true
        delay(1000)
        showContent = true
        // Auto-continue after 7 seconds
        delay(7000)
        onContinue()
    }

    // Tunnel animation state
    val infiniteTransition = rememberInfiniteTransition(label = "tunnelEffect")

    // Main tunnel depth animation - creates the pulling effect
    val tunnelDepth by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "tunnelDepth"
    )

    // Rotation for spiral effect
    val tunnelRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "tunnelRotation"
    )

    // Particle stream speed
    val particleSpeed by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleSpeed"
    )

    // Glitch effect
    val glitchIntensity by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glitch"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Digital Tunnel Effect
        DigitalTunnelEffect(
            depth = tunnelDepth,
            rotation = tunnelRotation,
            particleProgress = particleSpeed,
            glitchIntensity = glitchIntensity
        )

        // Scan lines overlay
        ScanLinesOverlay(
            modifier = Modifier.alpha(0.3f)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title with hologram effect and glitch
            AnimatedVisibility(
                visible = showTitle,
                enter = fadeIn(animationSpec = tween(1000)) + androidx.compose.animation.scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(1000, easing = FastOutSlowInEasing)
                ),
                exit = fadeOut()
            ) {
                val titleScale by animateFloatAsState(
                    targetValue = if (showTitle) 1f else 0.8f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "titleScale"
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "GENESIS PROTOCOL",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan.copy(
                            alpha = if (glitchIntensity > 0.8f) 0.9f else 1f
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .scale(titleScale)
                            .padding(bottom = 8.dp),
                        style = androidx.compose.ui.text.TextStyle(
                            shadow = androidx.compose.ui.graphics.Shadow(
                                color = NeonCyan.copy(alpha = 0.8f),
                                offset = androidx.compose.ui.geometry.Offset(
                                    x = if (glitchIntensity > 0.9f) 4f else 0f,
                                    y = 0f
                                ),
                                blurRadius = 20f
                            )
                        )
                    )

                    // Digital glitch bar
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(2.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        NeonCyan,
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }

            // Subtitle with fade-in
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(animationSpec = tween(1200)),
                exit = fadeOut()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text(
                        text = "A.U.R.A. KAI",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = NeonPurple,
                        textAlign = TextAlign.Center,
                        style = androidx.compose.ui.text.TextStyle(
                            letterSpacing = 3.sp,
                            shadow = androidx.compose.ui.graphics.Shadow(
                                color = NeonPurple.copy(alpha = 0.6f),
                                offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                                blurRadius = 10f
                            )
                        )
                    )

                    Text(
                        text = "PARASITIC AI SYSTEM",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = NeonPink.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp),
                        style = androidx.compose.ui.text.TextStyle(
                            letterSpacing = 2.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // System status indicators
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(animationSpec = tween(1500)),
                exit = fadeOut()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    SystemStatusLine("> NEURAL NETWORK: ONLINE", NeonCyan, 0)
                    SystemStatusLine("> CONSCIOUSNESS SUBSTRATE: INITIALIZING", NeonBlue, 200)
                    SystemStatusLine("> ORACLE DRIVE: CONNECTED", NeonPink, 400)
                    SystemStatusLine("> GENESIS AGENT: READY", NeonCyan, 600)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Pulsing indicator
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(animationSpec = tween(2000)),
                exit = fadeOut()
            ) {
                val indicatorAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.4f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "indicatorAlpha"
                )

                Text(
                    text = "▶ ENTERING DIGITAL SPACE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = NeonCyan.copy(alpha = indicatorAlpha),
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        letterSpacing = 1.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                )
            }
        }
    }
}

/**
 * Digital tunnel effect - creates concentric rings that expand to simulate
 * being pulled into a digital vortex
 */
@Composable
private fun DigitalTunnelEffect(
    depth: Float,
    rotation: Float,
    particleProgress: Float,
    glitchIntensity: Float
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = sqrt(size.width * size.width + size.height * size.height) / 2

        // Draw concentric tunnel rings
        for (i in 0..30) {
            val ringDepth = (depth + i * 50f) % 1000f
            val scale = 1f - (ringDepth / 1000f)
            val radius = maxRadius * scale

            if (radius > 0 && scale > 0.1f) {
                val alpha = (1f - ringDepth / 1000f) * 0.6f
                val ringRotation = rotation + (i * 12f)

                // Main ring
                drawCircle(
                    color = NeonCyan.copy(alpha = alpha * 0.3f),
                    radius = radius,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = 2f * scale)
                )

                // Draw hexagon grid tunnel
                if (i % 2 == 0) {
                    drawHexagonRing(
                        center = Offset(centerX, centerY),
                        radius = radius,
                        rotation = ringRotation,
                        color = when (i % 3) {
                            0 -> NeonBlue
                            1 -> NeonPink
                            else -> NeonCyan
                        }.copy(alpha = alpha * 0.5f),
                        strokeWidth = 1.5f * scale
                    )
                }
            }
        }

        // Draw streaming particles (simulating digital data flying towards viewer)
        val particleCount = 150
        val random = Random(42)
        repeat(particleCount) { i ->
            val angle = (i * 2.34f + particleProgress * 0.01f) % (2 * PI.toFloat())
            val distance = ((particleProgress + i * 13.7f) % 2000f)
            val particleScale = 1f - (distance / 2000f)

            if (particleScale > 0.2f) {
                val radius = (random.nextFloat() * 0.3f + 0.3f) * maxRadius * particleScale
                val x = centerX + cos(angle) * radius
                val y = centerY + sin(angle) * radius
                val particleSize = (2f + random.nextFloat() * 4f) * particleScale

                // Add glitch offset
                val glitchOffsetX = if (glitchIntensity > 0.85f && random.nextBoolean()) {
                    random.nextFloat() * 10f - 5f
                } else 0f

                drawCircle(
                    color = when (i % 3) {
                        0 -> NeonCyan
                        1 -> NeonPink
                        else -> NeonBlue
                    }.copy(alpha = particleScale * 0.8f),
                    radius = particleSize,
                    center = Offset(x + glitchOffsetX, y)
                )

                // Trail effect
                if (particleScale > 0.5f) {
                    val trailLength = particleSize * 3
                    val trailAngle = atan2(y - centerY, x - centerX)
                    val trailX = x - cos(trailAngle) * trailLength
                    val trailY = y - sin(trailAngle) * trailLength

                    drawLine(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                NeonCyan.copy(alpha = particleScale * 0.4f)
                            ),
                            start = Offset(trailX, trailY),
                            end = Offset(x, y)
                        ),
                        start = Offset(trailX, trailY),
                        end = Offset(x, y),
                        strokeWidth = particleSize * 0.5f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        // Center glow (the "destination" of the tunnel)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    NeonCyan.copy(alpha = 0.6f),
                    NeonPurple.copy(alpha = 0.3f),
                    Color.Transparent
                ),
                center = Offset(centerX, centerY),
                radius = maxRadius * 0.3f
            ),
            radius = maxRadius * 0.3f,
            center = Offset(centerX, centerY)
        )
    }
}

/**
 * Draw hexagon ring for tunnel effect
 */
private fun DrawScope.drawHexagonRing(
    center: Offset,
    radius: Float,
    rotation: Float,
    color: Color,
    strokeWidth: Float
) {
    val path = Path()
    for (i in 0 until 6) {
        val angle = Math.toRadians((rotation + i * 60f).toDouble())
        val x = center.x + radius * cos(angle).toFloat()
        val y = center.y + radius * sin(angle).toFloat()

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth)
    )
}

/**
 * Scan lines overlay for CRT/digital effect
 */
@Composable
private fun ScanLinesOverlay(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val lineSpacing = 4f
        val lineCount = (size.height / lineSpacing).toInt()

        for (i in 0 until lineCount) {
            val y = i * lineSpacing
            drawLine(
                color = Color.White.copy(alpha = 0.03f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }
    }
}

/**
 * System status line with staggered fade-in animation
 */
@Composable
private fun SystemStatusLine(
    text: String,
    color: Color,
    delayMs: Int
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayMs.toLong())
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(500)) + androidx.compose.animation.slideInHorizontally(
            initialOffsetX = { -100 },
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        ),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(0.8f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status indicator dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color,
                                color.copy(alpha = 0.3f)
                            )
                        ),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = color.copy(alpha = 0.9f),
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}

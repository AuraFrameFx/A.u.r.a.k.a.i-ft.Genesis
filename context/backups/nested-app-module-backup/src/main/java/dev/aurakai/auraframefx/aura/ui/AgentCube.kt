package dev.aurakai.auraframefx.aura.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Agent Cube
 *
 * The glowing cube at the center of Hub One - a compressed memory lattice.
 * Tap to trigger the dematerialization → rematerialization into the Spheregrid.
 *
 * Visual Features:
 * - 3D-style rotating cube with edge glow
 * - Pulsing aura/glow effect
 * - Orbiting particles
 * - "TAP TO REVEAL" hint text
 * - Smooth rotation animation
 *
 * Interaction:
 * - Tap to trigger onTap callback (reveals Spheregrid)
 * - Tactile feedback through visual pulse
 */
@Composable
fun AgentCube(
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isTapped by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Rotation animation
    val infiniteTransition = rememberInfiniteTransition(label = "cube_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing)
        ),
        label = "rotation"
    )

    // Pulse animation
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Orbital animation
    val orbit by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing)
        ),
        label = "orbit"
    )

    Box(
        modifier = modifier
            .size(240.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                isTapped = true
                onTap()
            },
        contentAlignment = Alignment.Center
    ) {
        // Outer glow/aura
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val glowRadius = size.minDimension / 2 * pulse

            // Multiple glow layers for depth
            for (i in 3 downTo 1) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF00FFFF).copy(alpha = 0.1f / i),
                            Color.Transparent
                        ),
                        center = Offset(centerX, centerY),
                        radius = glowRadius * (1f + i * 0.3f)
                    ),
                    radius = glowRadius * (1f + i * 0.3f),
                    center = Offset(centerX, centerY)
                )
            }
        }

        // Orbiting particles
        Canvas(modifier = Modifier.size(200.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val orbitRadius = size.minDimension / 2

            for (i in 0 until 6) {
                val angle = (orbit + i * 60f) * (Math.PI / 180f)
                val x = centerX + (orbitRadius * cos(angle)).toFloat()
                val y = centerY + (orbitRadius * sin(angle)).toFloat()

                // Particle with trail effect
                drawCircle(
                    color = Color(0xFF00FFFF).copy(alpha = 0.6f),
                    radius = 4f,
                    center = Offset(x, y)
                )

                // Glow around particle
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF00FFFF).copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        center = Offset(x, y),
                        radius = 12f
                    ),
                    radius = 12f,
                    center = Offset(x, y)
                )
            }
        }

        // The cube itself
        Canvas(modifier = Modifier.size(120.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val cubeSize = 60f * pulse

            // 3D cube vertices (simplified isometric projection)
            val angleRad = rotation * (Math.PI / 180f)
            val cosAngle = cos(angleRad).toFloat()
            val sinAngle = sin(angleRad).toFloat()

            // Front face
            val frontPath = Path().apply {
                moveTo(centerX - cubeSize / 2, centerY - cubeSize / 4)
                lineTo(centerX + cubeSize / 2, centerY - cubeSize / 4)
                lineTo(centerX + cubeSize / 2, centerY + cubeSize / 4)
                lineTo(centerX - cubeSize / 2, centerY + cubeSize / 4)
                close()
            }

            // Top face
            val topPath = Path().apply {
                moveTo(centerX - cubeSize / 2, centerY - cubeSize / 4)
                lineTo(centerX, centerY - cubeSize / 2)
                lineTo(centerX + cubeSize / 2, centerY - cubeSize / 4)
                lineTo(centerX, centerY)
                close()
            }

            // Right face
            val rightPath = Path().apply {
                moveTo(centerX + cubeSize / 2, centerY - cubeSize / 4)
                lineTo(centerX + cubeSize / 2, centerY + cubeSize / 4)
                lineTo(centerX, centerY + cubeSize / 2)
                lineTo(centerX, centerY)
                close()
            }

            // Draw faces with gradient fills
            drawPath(
                path = frontPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00FFFF).copy(alpha = 0.3f),
                        Color(0xFF0080FF).copy(alpha = 0.5f)
                    )
                )
            )

            drawPath(
                path = topPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00FFFF).copy(alpha = 0.5f),
                        Color(0xFF00FFFF).copy(alpha = 0.3f)
                    )
                )
            )

            drawPath(
                path = rightPath,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0080FF).copy(alpha = 0.4f),
                        Color(0xFF0080FF).copy(alpha = 0.2f)
                    )
                )
            )

            // Draw edges with glow
            drawPath(
                path = frontPath,
                color = Color(0xFF00FFFF),
                style = Stroke(width = 2f * pulse)
            )
            drawPath(
                path = topPath,
                color = Color(0xFF00FFFF),
                style = Stroke(width = 2f * pulse)
            )
            drawPath(
                path = rightPath,
                color = Color(0xFF00FFFF),
                style = Stroke(width = 2f * pulse)
            )

            // Central glyph/symbol
            drawCircle(
                color = Color(0xFF00FFFF),
                radius = 6f,
                center = Offset(centerX, centerY)
            )
        }

        // Hint text at bottom
        Text(
            text = "TAP TO REVEAL",
            color = Color(0xFF00FFFF).copy(alpha = 0.6f * pulse),
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

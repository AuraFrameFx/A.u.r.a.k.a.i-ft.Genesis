package dev.aurakai.auraframefx.aura.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Hub Fragment Component
 *
 * Each fragment is a living shard of digital consciousness - a gateway
 * to a specific domain within the Genesis lattice.
 *
 * Features:
 * - Animated glyph background (custom animation until Lottie assets added)
 * - Glowing title with shadow effects
 * - Descriptive text
 * - "Enter Domain" button with cyberpunk styling
 * - Reactive color theming per hub
 */
@Composable
fun HubFragment(
    hubData: HubData,
    onEnter: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27), // Deep space blue
                        Color(0xFF1A1A2E), // Dark purple-blue
                        Color(0xFF0A0E27)
                    )
                )
            )
    ) {
        // Animated background glyph pattern
        GlyphBackgroundAnimation(
            glyphColor = hubData.glyphColor,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.15f)
        )

        // Content overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pulsing glyph symbol
            PulsingGlyph(
                color = hubData.glyphColor,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Glowing title
            Text(
                text = hubData.title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = hubData.glyphColor,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = hubData.glyphColor,
                        blurRadius = 30f
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = hubData.description,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Enter domain button
            EnterDomainButton(
                glyphColor = hubData.glyphColor,
                onClick = onEnter
            )
        }

        // Corner accents
        CornerAccents(accentColor = hubData.accentColor)
    }
}

/**
 * Pulsing glyph symbol in the center
 */
@Composable
private fun PulsingGlyph(
    color: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glyph_pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing)
        ),
        label = "rotation"
    )

    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2 * pulse

        // Draw orbiting particles
        for (i in 0 until 8) {
            val angle = (rotation + i * 45f) * (Math.PI / 180f)
            val x = centerX + (radius * cos(angle)).toFloat()
            val y = centerY + (radius * sin(angle)).toFloat()

            drawCircle(
                color = color.copy(alpha = pulse),
                radius = 4f,
                center = Offset(x, y)
            )
        }

        // Central glow
        drawCircle(
            color = color.copy(alpha = 0.3f),
            radius = radius * 0.6f,
            center = Offset(centerX, centerY)
        )

        // Core
        drawCircle(
            color = color,
            radius = 8f * pulse,
            center = Offset(centerX, centerY)
        )
    }
}

/**
 * Animated background glyph pattern
 */
@Composable
private fun GlyphBackgroundAnimation(
    glyphColor: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bg_animation")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing)
        ),
        label = "offset"
    )

    Canvas(modifier = modifier) {
        val spacing = 80f
        val cols = (size.width / spacing).toInt() + 2
        val rows = (size.height / spacing).toInt() + 2

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val x = col * spacing + (offset % spacing)
                val y = row * spacing

                drawCircle(
                    color = glyphColor.copy(alpha = 0.1f),
                    radius = 2f,
                    center = Offset(x, y)
                )
            }
        }
    }
}

/**
 * Enter Domain button with cyberpunk styling
 */
@Composable
private fun EnterDomainButton(
    glyphColor: Color,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "button_glow")
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = glyphColor.copy(alpha = glow)
        ),
        border = BorderStroke(2.dp, glyphColor),
        modifier = Modifier
            .width(240.dp)
            .height(56.dp)
    ) {
        Text(
            text = "ENTER DOMAIN",
            color = glyphColor,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            fontSize = 16.sp
        )
    }
}

/**
 * Corner accent decorations
 */
@Composable
private fun BoxScope.CornerAccents(accentColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "corner_pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Top-left corner
    Canvas(
        modifier = Modifier
            .align(Alignment.TopStart)
            .size(60.dp)
            .padding(16.dp)
    ) {
        drawLine(
            color = accentColor.copy(alpha = pulse),
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 2f
        )
        drawLine(
            color = accentColor.copy(alpha = pulse),
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 2f
        )
    }

    // Bottom-right corner
    Canvas(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .size(60.dp)
            .padding(16.dp)
    ) {
        drawLine(
            color = accentColor.copy(alpha = pulse),
            start = Offset(size.width, size.height),
            end = Offset(0f, size.height),
            strokeWidth = 2f
        )
        drawLine(
            color = accentColor.copy(alpha = pulse),
            start = Offset(size.width, size.height),
            end = Offset(size.width, 0f),
            strokeWidth = 2f
        )
    }
}

package dev.aurakai.auraframefx.aura.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Synthwave/Vaporwave aesthetic components matching the gate card designs.
 *
 * Design Language:
 * - Purple grid floor (synthwave)
 * - Black starfield background
 * - Neon outline typography (cyan)
 * - Cyan rounded cards with rough edges
 * - Magenta headers, cyan body text
 */

// ========== COLOR PALETTE ==========
object SynthwaveColors {
    val Cyan = Color(0xFF00D9FF)
    val Magenta = Color(0xFFFF00FF)
    val Pink = Color(0xFFFF0080)
    val Yellow = Color(0xFFFFFF00)
    val Orange = Color(0xFFFFAA00)
    val Purple = Color(0xFFAA00FF)
    val GridPurple = Color(0xFFFF00FF)
    val Black = Color(0xFF000000)
}

/**
 * Starfield Background
 * Black background with scattered small dots/stars
 */
@Composable
fun StarfieldBackground(modifier: Modifier = Modifier) {
    val stars = remember {
        List(100) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 2f + 1f,
                alpha = Random.nextFloat() * 0.5f + 0.3f
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(SynthwaveColors.Black)

        stars.forEach { star ->
            drawCircle(
                color = Color.White.copy(alpha = star.alpha),
                radius = star.size,
                center = Offset(
                    x = star.x * size.width,
                    y = star.y * size.height
                )
            )
        }
    }
}

private data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val alpha: Float
)

/**
 * Purple Grid Floor
 * Classic synthwave perspective grid
 */
@Composable
fun BoxScope.PurpleGridFloor(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val gridColor = SynthwaveColors.GridPurple.copy(alpha = 0.3f)
        val spacing = 40f

        // Horizontal lines (perspective)
        for (i in 0..10) {
            val y = size.height - (i * spacing)
            val scale = 1f - (i * 0.05f)
            val lineWidth = size.width * scale
            val startX = (size.width - lineWidth) / 2

            drawLine(
                color = gridColor,
                start = Offset(startX, y),
                end = Offset(startX + lineWidth, y),
                strokeWidth = 1.5f
            )
        }

        // Vertical lines (perspective)
        for (i in -10..10) {
            val path = Path().apply {
                val startX = size.width / 2 + (i * spacing)
                moveTo(startX, size.height)
                lineTo(size.width / 2 + (i * spacing * 0.3f), 0f)
            }
            drawPath(
                path = path,
                color = gridColor,
                style = Stroke(width = 1.5f)
            )
        }
    }
}

/**
 * Neon Outline Text
 * Hollow outlined letters with glow effect
 */
@Composable
fun NeonOutlineText(
    text: String,
    color: Color = SynthwaveColors.Cyan,
    fontSize: Int = 48,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "neon_glow")
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(modifier = modifier) {
        // Outer glow
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Transparent,
                drawStyle = Stroke(
                    width = 6f,
                    miter = 4f,
                    join = StrokeJoin.Round
                ),
                shadow = Shadow(
                    color = color.copy(alpha = glow * 0.8f),
                    blurRadius = 20f
                )
            )
        )

        // Main outline
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Transparent,
                drawStyle = Stroke(
                    width = 3f,
                    miter = 4f,
                    join = StrokeJoin.Round
                )
            ),
            modifier = Modifier.drawBehind {
                drawContext.canvas.nativeCanvas.apply {
                    val paint = android.graphics.Paint().apply {
                        style = android.graphics.Paint.Style.STROKE
                        strokeWidth = 3f
                        this.color = color.toArgb()
                    }
                }
            }
        )

        // Inner shadow for depth
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold,
                color = color.copy(alpha = 0.3f),
                drawStyle = Stroke(
                    width = 1.5f,
                    join = StrokeJoin.Round
                )
            )
        )
    }
}

/**
 * Cyan Gate Card
 * Rounded rectangle card with rough/distressed edges
 */
@Composable
fun GateCard(
    welcomeText: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.85f)
            .wrapContentHeight()
            .background(
                color = SynthwaveColors.Cyan.copy(alpha = 0.1f),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 2.dp,
                color = SynthwaveColors.Cyan,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(24.dp)
    ) {
        Column {
            // Welcome header in magenta
            Text(
                text = welcomeText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SynthwaveColors.Magenta,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description in cyan
            Text(
                text = description,
                fontSize = 14.sp,
                color = SynthwaveColors.Cyan,
                lineHeight = 20.sp
            )
        }
    }
}

/**
 * Neon Icon
 * Glowing icon with outline effect
 */
@Composable
fun NeonIcon(
    icon: @Composable () -> Unit,
    color: Color,
    size: Int = 80,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "icon_glow")
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = modifier
            .size(size.dp)
            .drawBehind {
                // Outer glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha = glow * 0.3f),
                            Color.Transparent
                        ),
                        radius = size.dp.toPx()
                    ),
                    radius = size.dp.toPx()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

/**
 * Paint Brush Stroke
 * Glitch/paint stroke effect for Collab-Canvas
 */
@Composable
fun PaintStroke(
    color: Color,
    width: Float,
    height: Float,
    rotation: Float,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
    ) {
        rotate(rotation) {
            // Main stroke
            drawRoundRect(
                color = color.copy(alpha = 0.8f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(height.dp.toPx() / 2)
            )

            // Highlight
            drawRoundRect(
                color = color.copy(alpha = 0.4f),
                topLeft = Offset(0f, 0f),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width,
                    height = size.height * 0.3f
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(height.dp.toPx() / 2)
            )
        }
    }
}

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

// Cyberpunk UI Engine colors
private val EngineNeonCyan = Color(0xFF00F0FF)
private val EngineNeonPink = Color(0xFFFF00FF)
private val EngineNeonBlue = Color(0xFF0080FF)
private val EngineNeonGreen = Color(0xFF00FF88)
private val EngineNeonPurple = Color(0xFF8000FF)
private val EngineNeonYellow = Color(0xFFFFFF00)
private val EngineDarkBg = Color(0xFF0A0A12)
private val EngineDarkerBg = Color(0xFF050508)

/**
 * UI Engine Screen - Dynamic UI component builder and theme previewer
 * Features: Component library, live preview, theme editor, animation tester
 */
@Composable
fun UiEngineScreen() {
    var selectedCategory by remember { mutableStateOf(ComponentCategory.BUTTONS) }
    var selectedComponent by remember { mutableStateOf<UIComponent?>(null) }
    var previewTheme by remember { mutableStateOf(PreviewTheme.CYBERPUNK) }

    // Animation state
    val infiniteTransition = rememberInfiniteTransition(label = "ui_engine")
    val waveProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EngineDarkBg)
    ) {
        // Animated background
        WaveBackground(waveProgress.value)

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Left sidebar: Component categories
            ComponentCategoryPanel(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                glowPulse = glowPulse.value,
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Middle: Component list
            ComponentListPanel(
                category = selectedCategory,
                selectedComponent = selectedComponent,
                onComponentSelected = { selectedComponent = it },
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Right: Preview and properties
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                // Theme selector
                ThemeSelectorBar(
                    selectedTheme = previewTheme,
                    onThemeSelected = { previewTheme = it },
                    glowPulse = glowPulse.value
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Component preview
                ComponentPreviewPanel(
                    component = selectedComponent ?: getDefaultComponent(),
                    theme = previewTheme,
                    glowPulse = glowPulse.value,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Properties panel
                PropertiesPanel(
                    component = selectedComponent,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun WaveBackground(progress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val waveHeight = 30f
        val waveCount = 5

        for (i in 0 until waveCount) {
            val yOffset = (size.height / waveCount) * i
            val phase = progress * 2 * Math.PI + (i * 0.5)

            for (x in 0..size.width.toInt() step 20) {
                val y = yOffset + waveHeight * sin((x / 100.0) + phase).toFloat()
                val nextX = (x + 20).coerceAtMost(size.width.toInt())
                val nextY = yOffset + waveHeight * sin((nextX / 100.0) + phase).toFloat()

                drawLine(
                    color = EngineNeonCyan.copy(alpha = 0.05f * (1f - i / waveCount.toFloat())),
                    start = Offset(x.toFloat(), y),
                    end = Offset(nextX.toFloat(), nextY),
                    strokeWidth = 2f
                )
            }
        }
    }
}

@Composable
private fun ComponentCategoryPanel(
    selectedCategory: ComponentCategory,
    onCategorySelected: (ComponentCategory) -> Unit,
    glowPulse: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(EngineDarkerBg, RoundedCornerShape(12.dp))
            .border(1.dp, EngineNeonCyan.copy(alpha = glowPulse * 0.5f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "CATEGORIES",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = EngineNeonCyan
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        ComponentCategory.values().forEach { category ->
            CategoryCard(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
private fun CategoryCard(
    category: ComponentCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) EngineNeonCyan.copy(alpha = 0.2f) else Color.Transparent
    val borderColor = if (isSelected) EngineNeonCyan else EngineNeonCyan.copy(alpha = 0.3f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = category.icon,
            contentDescription = null,
            tint = if (isSelected) EngineNeonCyan else Color.Gray,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = category.displayName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) EngineNeonCyan else Color.Gray
            )
        )
    }
}

@Composable
private fun ComponentListPanel(
    category: ComponentCategory,
    selectedComponent: UIComponent?,
    onComponentSelected: (UIComponent) -> Unit,
    modifier: Modifier = Modifier
) {
    val components = remember(category) { getComponentsForCategory(category) }

    Column(
        modifier = modifier
            .background(EngineDarkerBg, RoundedCornerShape(12.dp))
            .border(1.dp, EngineNeonPurple.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "COMPONENTS",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = EngineNeonPurple
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(components) { component ->
                ComponentListItem(
                    component = component,
                    isSelected = component == selectedComponent,
                    onClick = { onComponentSelected(component) }
                )
            }
        }
    }
}

@Composable
private fun ComponentListItem(
    component: UIComponent,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) EngineNeonPurple.copy(alpha = 0.2f) else Color.Transparent
    val borderColor = if (isSelected) EngineNeonPurple else Color.Transparent

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(6.dp))
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Text(
            text = component.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = if (isSelected) EngineNeonPurple else Color.White
            )
        )

        Text(
            text = component.description,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
private fun ThemeSelectorBar(
    selectedTheme: PreviewTheme,
    onThemeSelected: (PreviewTheme) -> Unit,
    glowPulse: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(EngineDarkerBg, RoundedCornerShape(8.dp))
            .border(1.dp, EngineNeonBlue.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "THEME:",
            style = MaterialTheme.typography.labelMedium,
            color = EngineNeonBlue
        )

        PreviewTheme.values().forEach { theme ->
            ThemeChip(
                theme = theme,
                isSelected = theme == selectedTheme,
                onClick = { onThemeSelected(theme) },
                glowPulse = glowPulse
            )
        }
    }
}

@Composable
private fun ThemeChip(
    theme: PreviewTheme,
    isSelected: Boolean,
    onClick: () -> Unit,
    glowPulse: Float
) {
    val backgroundColor =
        if (isSelected) theme.accentColor.copy(alpha = 0.3f) else Color.Transparent
    val borderColor =
        if (isSelected) theme.accentColor.copy(alpha = glowPulse) else theme.accentColor.copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = theme.displayName,
            style = MaterialTheme.typography.labelMedium.copy(
                color = if (isSelected) theme.accentColor else Color.Gray
            )
        )
    }
}

@Composable
private fun ComponentPreviewPanel(
    component: UIComponent,
    theme: PreviewTheme,
    glowPulse: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(EngineDarkerBg, RoundedCornerShape(12.dp))
            .border(1.dp, EngineNeonPink.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PREVIEW",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = EngineNeonPink
                )
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    imageVector = Icons.Filled.Smartphone,
                    contentDescription = "Mobile",
                    tint = EngineNeonPink,
                    modifier = Modifier.size(20.dp)
                )
                Icon(
                    imageVector = Icons.Filled.DarkMode,
                    contentDescription = "Dark mode",
                    tint = EngineNeonPink.copy(alpha = glowPulse),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Preview canvas
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(theme.backgroundColor, RoundedCornerShape(8.dp))
                .border(2.dp, theme.accentColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            // Render component preview based on type
            when (component.category) {
                ComponentCategory.BUTTONS -> PreviewButton(component, theme)
                ComponentCategory.CARDS -> PreviewCard(component, theme)
                ComponentCategory.INPUTS -> PreviewInput(component, theme)
                ComponentCategory.LAYOUTS -> PreviewLayout(component, theme)
                ComponentCategory.ANIMATIONS -> PreviewAnimation(component, theme, glowPulse)
                ComponentCategory.EFFECTS -> PreviewEffect(component, theme, glowPulse)
            }
        }
    }
}

@Composable
private fun PreviewButton(component: UIComponent, theme: PreviewTheme) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(containerColor = theme.accentColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(48.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = component.name, color = Color.White)
    }
}

@Composable
private fun PreviewCard(component: UIComponent, theme: PreviewTheme) {
    Card(
        modifier = Modifier.size(200.dp, 150.dp),
        colors = CardDefaults.cardColors(containerColor = theme.backgroundColor.copy(alpha = 0.8f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, theme.accentColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = component.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = theme.accentColor
                )
            )
            Text(
                text = "Card content preview",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun PreviewInput(component: UIComponent, theme: PreviewTheme) {
    OutlinedTextField(
        value = "Sample text",
        onValueChange = {},
        label = { Text(component.name) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = theme.accentColor,
            unfocusedBorderColor = theme.accentColor.copy(alpha = 0.5f),
            focusedLabelColor = theme.accentColor
        ),
        modifier = Modifier.width(250.dp)
    )
}

@Composable
private fun PreviewLayout(component: UIComponent, theme: PreviewTheme) {
    Row(
        modifier = Modifier.fillMaxWidth(0.6f),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .background(theme.accentColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .border(1.dp, theme.accentColor, RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
private fun PreviewAnimation(component: UIComponent, theme: PreviewTheme, glowPulse: Float) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(theme.accentColor.copy(alpha = 0.2f * glowPulse), RoundedCornerShape(50.dp))
            .border(2.dp, theme.accentColor.copy(alpha = glowPulse), RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.AutoAwesome,
            contentDescription = null,
            tint = theme.accentColor.copy(alpha = glowPulse),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun PreviewEffect(component: UIComponent, theme: PreviewTheme, glowPulse: Float) {
    Canvas(modifier = Modifier.size(150.dp)) {
        // Particle effect visualization
        for (i in 0 until 20) {
            val angle = (i / 20f) * 2 * Math.PI
            val radius = 50f + (glowPulse * 20f)
            val x = center.x + (radius * cos(angle).toFloat())
            val y = center.y + (radius * sin(angle).toFloat())

            drawCircle(
                color = theme.accentColor.copy(alpha = glowPulse * 0.7f),
                radius = 4f,
                center = Offset(x, y)
            )
        }
    }
}

@Composable
private fun PropertiesPanel(
    component: UIComponent?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(EngineDarkerBg, RoundedCornerShape(12.dp))
            .border(1.dp, EngineNeonGreen.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "PROPERTIES",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = EngineNeonGreen
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (component != null) {
            PropertyRow("Name", component.name)
            PropertyRow("Category", component.category.displayName)
            PropertyRow("Type", component.type)
            PropertyRow("Status", "Ready")
        } else {
            Text(
                text = "Select a component to view properties",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun PropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = EngineNeonGreen
            )
        )
    }
}

// Data models
private enum class ComponentCategory(val displayName: String, val icon: ImageVector) {
    BUTTONS("Buttons", Icons.Filled.TouchApp),
    CARDS("Cards", Icons.Filled.ViewAgenda),
    INPUTS("Inputs", Icons.Filled.Edit),
    LAYOUTS("Layouts", Icons.Filled.Dashboard),
    ANIMATIONS("Animations", Icons.Filled.Animation),
    EFFECTS("Effects", Icons.Filled.AutoAwesome)
}

private enum class PreviewTheme(
    val displayName: String,
    val accentColor: Color,
    val backgroundColor: Color
) {
    CYBERPUNK("Cyberpunk", EngineNeonCyan, EngineDarkBg),
    NEON("Neon", EngineNeonPink, EngineDarkerBg),
    MATRIX("Matrix", EngineNeonGreen, Color.Black),
    SUNSET("Sunset", EngineNeonYellow, Color(0xFF1A0A2E))
}

private data class UIComponent(
    val name: String,
    val description: String,
    val category: ComponentCategory,
    val type: String
)

private fun getComponentsForCategory(category: ComponentCategory): List<UIComponent> {
    return when (category) {
        ComponentCategory.BUTTONS -> listOf(
            UIComponent("Neon Button", "Glowing cyberpunk button", category, "Interactive"),
            UIComponent("Hologram Button", "3D holographic effect", category, "Interactive"),
            UIComponent("Pulse Button", "Animated pulse effect", category, "Interactive")
        )

        ComponentCategory.CARDS -> listOf(
            UIComponent("Glass Card", "Translucent glassmorphism", category, "Container"),
            UIComponent("Neon Card", "Glowing border card", category, "Container"),
            UIComponent("Data Panel", "System data display", category, "Container")
        )

        ComponentCategory.INPUTS -> listOf(
            UIComponent("Cyber TextField", "Neon text input", category, "Input"),
            UIComponent("Terminal Input", "Terminal-style input", category, "Input"),
            UIComponent("Voice Input", "Voice recognition input", category, "Input")
        )

        ComponentCategory.LAYOUTS -> listOf(
            UIComponent("Grid Layout", "Responsive grid system", category, "Layout"),
            UIComponent("Flex Layout", "Flexible box layout", category, "Layout"),
            UIComponent("Split Panel", "Resizable split view", category, "Layout")
        )

        ComponentCategory.ANIMATIONS -> listOf(
            UIComponent("Fade Transition", "Smooth fade effect", category, "Animation"),
            UIComponent("Slide Animation", "Sliding motion", category, "Animation"),
            UIComponent("Morph Effect", "Shape transformation", category, "Animation")
        )

        ComponentCategory.EFFECTS -> listOf(
            UIComponent("Particle System", "Dynamic particles", category, "Effect"),
            UIComponent("Glow Effect", "Neon glow overlay", category, "Effect"),
            UIComponent("Scan Lines", "CRT scan line effect", category, "Effect")
        )
    }
}

private fun getDefaultComponent(): UIComponent {
    return UIComponent(
        name = "Default Preview",
        description = "Select a component to preview",
        category = ComponentCategory.BUTTONS,
        type = "None"
    )
}

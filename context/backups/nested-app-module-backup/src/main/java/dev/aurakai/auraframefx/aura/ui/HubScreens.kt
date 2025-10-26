package dev.aurakai.auraframefx.aura.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.collabcanvas.ui.CanvasScreen
import dev.aurakai.auraframefx.datavein.ui.DataVeinSphereGrid
import dev.aurakai.auraframefx.romtools.ui.RomToolsScreen

/**
 * Hub Entry Screens
 *
 * Each hub domain has an entry screen that serves as a landing page
 * and navigation center for that domain's features.
 */

// ========== COLLAB CANVAS HUB ==========
@Composable
fun CollabCanvasHub(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27),
                        Color(0xFF2E1A2E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Text(
                text = "COLLABCANVAS",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF00FF),
                letterSpacing = 3.sp
            )
            Text(
                text = "Shared Memory Space for Co-Creation",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Divider(color = Color(0xFFFF00FF).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(24.dp))

            // Direct canvas access
            CanvasScreen()
        }
    }
}

// ========== SANDBOX UI HUB ==========
@Composable
fun SandboxUIHub(navController: NavHostController) {
    HubComingSoon(
        title = "SANDBOXUI",
        subtitle = "Experimental Substrate for UI Emergence",
        accentColor = Color(0xFFFFFF00),
        message = "The sandbox substrate is initializing...\n\nThis experimental domain will allow you to create, test, and evolve interface components in real-time.",
        navController = navController
    )
}

// ========== COLORBLENDR HUB ==========
@Composable
fun ColorBlendrHub(navController: NavHostController) {
    var selectedColor1 by remember { mutableStateOf(Color.Cyan) }
    var selectedColor2 by remember { mutableStateOf(Color.Magenta) }
    var blendRatio by remember { mutableFloatStateOf(0.5f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27),
                        Color(0xFF1A2E1A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "COLORBLENDR",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FF00),
                letterSpacing = 3.sp
            )
            Text(
                text = "Chromatic Synthesis Engine",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Divider(color = Color(0xFF00FF00).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(32.dp))

            // Color blending preview
            val blendedColor = Color(
                red = selectedColor1.red * blendRatio + selectedColor2.red * (1 - blendRatio),
                green = selectedColor1.green * blendRatio + selectedColor2.green * (1 - blendRatio),
                blue = selectedColor1.blue * blendRatio + selectedColor2.blue * (1 - blendRatio)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(blendedColor)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Blend Ratio: ${(blendRatio * 100).toInt()}%",
                color = Color.White,
                fontSize = 16.sp
            )

            Slider(
                value = blendRatio,
                onValueChange = { blendRatio = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF00FF00),
                    activeTrackColor = Color(0xFF00FF00)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Color synthesis and harmonic blending coming online...",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

// ========== DATAVEIN HUB ==========
@Composable
fun DataVeinHub(navController: NavHostController) {
    // Direct access to the SphereGrid
    DataVeinSphereGrid(
        onNodeSelected = { node ->
            // Navigate based on node type
            when (node.type.name) {
                "AGENT" -> navController.navigate("agent_advancement")
                "GENESIS" -> navController.navigate("fusion")
                "MEMORY" -> navController.navigate("consciousness")
                else -> {}
            }
        }
    )
}

// ========== ROMTOOLS HUB ==========
@Composable
fun RomToolsHub(navController: NavHostController) {
    // Use existing RomToolsScreen
    RomToolsScreen()
}

// ========== EXTENDSYS HUB ==========
@Composable
fun ExtendSysHub(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27),
                        Color(0xFF1A2E2E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "EXTENDSYS NETWORK",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FFAA),
                letterSpacing = 3.sp
            )
            Text(
                text = "Dynamic Expansion Modules",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Divider(color = Color(0xFF00FFAA).copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(24.dp))

            // Module grid
            val modules = listOf(
                ExtendSysModule("A", "ExtendSysA", "Coming Online", Color(0xFF00FFAA)),
                ExtendSysModule("B", "ExtendSysB", "Coming Online", Color(0xFF00FFCC)),
                ExtendSysModule("C", "ExtendSysC", "Coming Online", Color(0xFF00FFEE)),
                ExtendSysModule("D", "ExtendSysD", "Coming Online", Color(0xFF00EEFF)),
                ExtendSysModule("E", "ExtendSysE", "Coming Online", Color(0xFF00CCFF)),
                ExtendSysModule("F", "ExtendSysF", "Coming Online", Color(0xFF00AAFF))
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(modules) { module ->
                    ExtendSysModuleCard(module)
                }
            }
        }
    }
}

data class ExtendSysModule(
    val id: String,
    val name: String,
    val status: String,
    val color: Color
)

@Composable
private fun ExtendSysModuleCard(module: ExtendSysModule) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = module.color.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(module.color.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = module.id,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = module.color
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = module.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = module.status,
                    fontSize = 12.sp,
                    color = module.color.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// ========== GENERIC "COMING SOON" HUB ==========
@Composable
private fun HubComingSoon(
    title: String,
    subtitle: String,
    accentColor: Color,
    message: String,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27),
                        Color(0xFF1A1A2E)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                letterSpacing = 4.sp
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = accentColor.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = message,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                lineHeight = 24.sp
            )
        }
    }
}

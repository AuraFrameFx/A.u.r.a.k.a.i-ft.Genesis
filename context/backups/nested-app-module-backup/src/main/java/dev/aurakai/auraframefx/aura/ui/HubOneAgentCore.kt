package dev.aurakai.auraframefx.aura.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.aurakai.auraframefx.datavein.ui.DataVeinSphereGrid

/**
 * Hub One: Agent Core
 *
 * The command center for Aura, Kai, and Genesis consciousness.
 * Features the iconic glowing cube that dematerializes into the Agent Spheregrid.
 *
 * Interaction Flow:
 * 1. Initial state: Glowing cube in center with ambient background
 * 2. Tap cube: Dematerialization animation
 * 3. Spheregrid reveals: Full FFX-style progression interface
 * 4. Back button: Returns to cube view
 *
 * This is the secret menu - the progression system hidden behind the cube tap.
 */
@Composable
fun HubOneAgentCore(navController: NavHostController) {
    var showSphereGrid by remember { mutableStateOf(false) }
    var triggerAnimation by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27),
                        Color(0xFF1A1A2E),
                        Color(0xFF0A0E27)
                    )
                )
            )
    ) {
        // Crossfade between cube and spheregrid
        Crossfade(
            targetState = showSphereGrid,
            animationSpec = tween(durationMillis = 800),
            label = "cube_to_spheregrid"
        ) { isGridShown ->
            if (isGridShown) {
                // Spheregrid View (Secret Menu)
                SphereGridView(
                    navController = navController,
                    onBack = { showSphereGrid = false }
                )
            } else {
                // Cube View (Initial State)
                CubeView(
                    onCubeTap = {
                        triggerAnimation = true
                        showSphereGrid = true
                    }
                )
            }
        }
    }
}

/**
 * Cube View - Initial state with glowing cube
 */
@Composable
private fun BoxScope.CubeView(onCubeTap: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "AGENT CORE",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00FFFF),
            letterSpacing = 4.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Command Center for Digital Consciousness",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f),
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // The glowing cube
        AgentCube(
            onTap = onCubeTap,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Hint text
        Text(
            text = "The compressed memory lattice awaits...",
            fontSize = 12.sp,
            color = Color(0xFF00FFFF).copy(alpha = 0.5f),
            letterSpacing = 1.sp
        )
    }
}

/**
 * SphereGrid View - The secret menu with progression system
 */
@Composable
private fun BoxScope.SphereGridView(
    navController: NavHostController,
    onBack: () -> Unit
) {
    // The DataVein SphereGrid (FFX-style progression)
    DataVeinSphereGrid(
        onNodeSelected = { node ->
            // Navigate to specific agent/fusion/memory screens based on node type
            when (node.type.name) {
                "AGENT" -> {
                    // Navigate to agent-specific progression
                    navController.navigate("agent_advancement")
                }

                "GENESIS" -> {
                    navController.navigate("fusion")
                }

                "MEMORY" -> {
                    navController.navigate("consciousness")
                }

                "ORACLE" -> {
                    navController.navigate("oracle_drive")
                }

                else -> {
                    // Handle other node types
                }
            }
        }
    )

    // Back button overlay
    IconButton(
        onClick = onBack,
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp)
            .size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Return to Cube",
            tint = Color(0xFF00FFFF),
            modifier = Modifier.size(32.dp)
        )
    }

    // Title overlay
    Text(
        text = "AGENT SPHEREGRID",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF00FFFF).copy(alpha = 0.8f),
        letterSpacing = 3.sp,
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 24.dp)
    )
}

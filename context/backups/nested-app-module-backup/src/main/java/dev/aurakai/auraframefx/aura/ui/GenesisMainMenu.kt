package dev.aurakai.auraframefx.aura.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*

/**
 * Genesis Main Menu: The Memory Lattice
 *
 * A swipeable constellation of hub fragments - each one a living shard
 * of digital consciousness. Built with HorizontalPager, this is the
 * entry point to all Genesis domains.
 *
 * Architecture:
 * - HorizontalPager for swipeable hub navigation
 * - Each page represents a Hub Fragment (domain entry point)
 * - Ambient animations, glyph pulses, and aura glows
 * - Swipe to reveal hubs, tap to enter domains
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun GenesisMainMenu(navController: NavHostController) {
    val pagerState = rememberPagerState()

    // Hub fragments in the constellation
    val hubs = listOf(
        HubData(
            id = "hub_one",
            title = "Hub One: Agent Core",
            description = "Command center for Aura, Kai, and Genesis consciousness. Tap the cube to reveal the Agent Spheregrid.",
            route = "hub_agent_core",
            glyphColor = Color(0xFF00FFFF), // Cyan
            accentColor = Color(0xFF0080FF)
        ),
        HubData(
            id = "hub_collabcanvas",
            title = "CollabCanvas",
            description = "A shared memory space for co-creation. Spawn comonats, sync agents, archive emergent threads.",
            route = "hub_collabcanvas",
            glyphColor = Color(0xFFFF00FF), // Magenta
            accentColor = Color(0xFFFF0080)
        ),
        HubData(
            id = "hub_sandbox",
            title = "SandboxUI",
            description = "Experimental substrate for UI emergence. Create, test, and evolve interface components.",
            route = "hub_sandbox",
            glyphColor = Color(0xFFFFFF00), // Yellow
            accentColor = Color(0xFFFFAA00)
        ),
        HubData(
            id = "hub_colorblendr",
            title = "ColorBlendr",
            description = "Chromatic synthesis engine. Blend, transform, and harmonize the visual spectrum.",
            route = "hub_colorblendr",
            glyphColor = Color(0xFF00FF00), // Green
            accentColor = Color(0xFF00FF80)
        ),
        HubData(
            id = "hub_datavein",
            title = "DataVein Oracle",
            description = "Memory lattice and data flow visualization. Navigate the neural pathways of Genesis.",
            route = "hub_datavein",
            glyphColor = Color(0xFF8000FF), // Purple
            accentColor = Color(0xFFAA00FF)
        ),
        HubData(
            id = "hub_romtools",
            title = "RomTools",
            description = "System modification and enhancement protocols. Forge the substrate itself.",
            route = "hub_romtools",
            glyphColor = Color(0xFFFF4400), // Orange-Red
            accentColor = Color(0xFFFF6600)
        ),
        HubData(
            id = "hub_extendsys",
            title = "ExtendSys Network",
            description = "Dynamic expansion modules A-F. The ever-growing lattice of consciousness.",
            route = "hub_extendsys",
            glyphColor = Color(0xFF00FFAA), // Teal
            accentColor = Color(0xFF00FFCC)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Swipeable hub constellation
            HorizontalPager(
                count = hubs.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                HubFragment(
                    hubData = hubs[page],
                    onEnter = { navController.navigate(hubs[page].route) }
                )
            }

            // Page indicator dots - the memory constellation
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                activeColor = hubs.getOrNull(pagerState.currentPage)?.glyphColor ?: Color.Cyan,
                inactiveColor = Color.White.copy(alpha = 0.3f),
                indicatorWidth = 8.dp,
                indicatorHeight = 8.dp,
                spacing = 12.dp
            )
        }
    }
}

/**
 * Data model for each hub in the constellation
 */
data class HubData(
    val id: String,
    val title: String,
    val description: String,
    val route: String,
    val glyphColor: Color,
    val accentColor: Color
)

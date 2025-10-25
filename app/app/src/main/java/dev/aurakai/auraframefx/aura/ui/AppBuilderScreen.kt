package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ui.components.HexagonGridBackground
import dev.aurakai.auraframefx.ui.theme.*

/**
 * App Builder Screen
 *
 * Dynamic cyberpunk UI for building and customizing applications.
 * Features component library, drag-and-drop interface, and real-time preview.
 */
@Composable
fun AppBuilderScreen(
    onNavigateBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(BuilderTab.COMPONENTS) }
    var selectedComponent by remember { mutableStateOf<AppComponent?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Animated background
        HexagonGridBackground(
            modifier = Modifier.alpha(0.2f),
            primaryColor = NeonBlue,
            secondaryColor = NeonPink,
            accentColor = NeonCyan
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top app bar
            BuilderTopBar(
                onNavigateBack = onNavigateBack
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                // Left sidebar - Component/Template selection
                BuilderSidebar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    selectedComponent = selectedComponent,
                    onComponentSelected = { selectedComponent = it },
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                )

                // Center - Canvas/Preview area
                BuilderCanvas(
                    selectedComponent = selectedComponent,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

                // Right sidebar - Properties/Settings
                BuilderPropertiesPanel(
                    selectedComponent = selectedComponent,
                    modifier = Modifier
                        .width(280.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
private fun BuilderTopBar(
    onNavigateBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF1A0B2E).copy(alpha = 0.9f),
                        Color.Black.copy(alpha = 0.9f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = NeonCyan.copy(alpha = 0.3f)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = NeonCyan
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "APP BUILDER",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = NeonCyan,
            style = androidx.compose.ui.text.TextStyle(
                letterSpacing = 2.sp
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Action buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CyberButton(
                text = "PREVIEW",
                icon = Icons.Default.PlayArrow,
                onClick = { /* TODO: Implement preview */ }
            )
            CyberButton(
                text = "EXPORT",
                icon = Icons.Default.Done,
                onClick = { /* TODO: Implement export */ }
            )
        }
    }
}

@Composable
private fun BuilderSidebar(
    selectedTab: BuilderTab,
    onTabSelected: (BuilderTab) -> Unit,
    selectedComponent: AppComponent?,
    onComponentSelected: (AppComponent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.7f))
            .border(
                width = 1.dp,
                color = NeonPurple.copy(alpha = 0.3f)
            )
            .padding(16.dp)
    ) {
        // Tab selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BuilderTab.values().forEach { tab ->
                TabButton(
                    text = tab.displayName,
                    isSelected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Component list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(getSampleComponents()) { component ->
                ComponentCard(
                    component = component,
                    isSelected = selectedComponent == component,
                    onClick = { onComponentSelected(component) }
                )
            }
        }
    }
}

@Composable
private fun BuilderCanvas(
    selectedComponent: AppComponent?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFF0A0A0A))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Grid background for canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A0B2E).copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            NeonCyan.copy(alpha = 0.5f),
                            NeonPurple.copy(alpha = 0.5f)
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            selectedComponent?.let { component ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = component.icon,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = component.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )

                    Text(
                        text = "Preview Area",
                        fontSize = 14.sp,
                        color = NeonPurple.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } ?: run {
                Text(
                    text = "SELECT A COMPONENT TO BEGIN",
                    fontSize = 16.sp,
                    color = NeonPurple.copy(alpha = 0.5f),
                    style = androidx.compose.ui.text.TextStyle(
                        letterSpacing = 1.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun BuilderPropertiesPanel(
    selectedComponent: AppComponent?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.7f))
            .border(
                width = 1.dp,
                color = NeonPink.copy(alpha = 0.3f)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "PROPERTIES",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = NeonPink,
            style = androidx.compose.ui.text.TextStyle(
                letterSpacing = 1.5.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        selectedComponent?.let { component ->
            PropertyItem("Name", component.name)
            PropertyItem("Type", component.type)
            PropertyItem("Category", component.category)
        } ?: run {
            Text(
                text = "No component selected",
                fontSize = 14.sp,
                color = NeonPurple.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun ComponentCard(
    component: AppComponent,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF1A0B2E).copy(alpha = 0.6f)
        else Color.Transparent,
        label = "componentCardBg"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) NeonCyan else NeonPurple.copy(alpha = 0.3f),
        label = "componentCardBorder"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = component.icon,
            contentDescription = null,
            tint = if (isSelected) NeonCyan else NeonPurple,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = component.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) NeonCyan else NeonPurple
            )

            Text(
                text = component.type,
                fontSize = 11.sp,
                color = NeonPurple.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun PropertyItem(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = NeonPurple.copy(alpha = 0.7f),
            style = androidx.compose.ui.text.TextStyle(
                letterSpacing = 1.sp
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontSize = 14.sp,
            color = NeonCyan,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A0B2E).copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) NeonCyan.copy(alpha = 0.2f) else Color.Transparent,
        label = "tabBg"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) NeonCyan else NeonPurple.copy(alpha = 0.3f),
        label = "tabBorder"
    )

    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) NeonCyan else NeonPurple,
        modifier = modifier
            .clickable(onClick = onClick)
            .background(backgroundColor, RoundedCornerShape(4.dp))
            .border(1.dp, borderColor, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        style = androidx.compose.ui.text.TextStyle(
            letterSpacing = 0.5.sp
        )
    )
}

@Composable
private fun CyberButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = NeonCyan.copy(alpha = 0.2f),
            contentColor = NeonCyan
        ),
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(4.dp),
                ambientColor = NeonCyan.copy(alpha = 0.3f)
            )
            .border(1.dp, NeonCyan, RoundedCornerShape(4.dp))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            style = androidx.compose.ui.text.TextStyle(
                letterSpacing = 1.sp
            )
        )
    }
}

// Data models
enum class BuilderTab(val displayName: String) {
    COMPONENTS("COMPONENTS"),
    TEMPLATES("TEMPLATES"),
    ASSETS("ASSETS")
}

data class AppComponent(
    val name: String,
    val type: String,
    val category: String,
    val icon: ImageVector
)

// Sample data
private fun getSampleComponents() = listOf(
    AppComponent("Button", "UI Component", "Input", Icons.Default.Add),
    AppComponent("Text Field", "UI Component", "Input", Icons.Default.Edit),
    AppComponent("Card", "UI Component", "Container", Icons.Default.Info),
    AppComponent("List", "UI Component", "Display", Icons.Default.List),
    AppComponent("Image", "UI Component", "Media", Icons.Default.Star),
    AppComponent("Navigation Bar", "UI Component", "Navigation", Icons.Default.Menu)
)

package com.example.activites_journal.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activites_journal.ui.theme.PrimaryBlue
import com.example.activites_journal.utils.glassCard

sealed class NavItem(val route: String, val icon: ImageVector, val label: String) {
    object HabitTracker : NavItem("habit_tracker", Icons.Default.CheckCircle, "Tracker")
    object MoodJournal : NavItem("mood_journal", Icons.Default.Edit, "Journal")
    object Analytics : NavItem("analytics", Icons.Default.BarChart, "Stats")
    object Settings : NavItem("settings", Icons.Default.Settings, "Settings")
}

@Composable
fun PillGlassNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        NavItem.HabitTracker,
        NavItem.MoodJournal,
        NavItem.Analytics,
        NavItem.Settings
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .glassCard(
                cornerRadius = 32.dp,
                alpha = null, // Use default from glassCard
                borderAlpha = null
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            val scale by animateFloatAsState(if (isSelected) 1.1f else 1f)
            
            val activeTabColor = PrimaryBlue
            val activeContentColor = Color.White
            val inactiveContentColor = Color.Gray

            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) activeTabColor else Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onNavigate(item.route) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .scale(scale),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) activeContentColor else inactiveContentColor.copy(alpha = 0.7f),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = item.label,
                        color = if (isSelected) activeContentColor else inactiveContentColor,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

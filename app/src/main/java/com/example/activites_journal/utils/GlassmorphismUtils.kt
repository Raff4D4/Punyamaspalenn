package com.example.activites_journal.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.activites_journal.ui.theme.*

@Composable
fun Modifier.glassBackground(): Modifier = this.background(
    color = MaterialTheme.colorScheme.background
)

@Composable
fun Modifier.glassCard(
    cornerRadius: Dp = 24.dp,
    isDark: Boolean = isSystemInDarkTheme(),
    alpha: Float? = null,
    borderAlpha: Float? = null
): Modifier {
    val glassColor = if (isDark) MaterialTheme.colorScheme.surface else Color.White
    val effectiveAlpha = alpha ?: (if (isDark) 0.65f else 0.85f)
    val borderColor = if (isDark) {
        Color.White.copy(alpha = borderAlpha ?: 0.15f)
    } else {
        Color(0xFFCBD5E1)
    }

    return this
        .shadow(
            elevation = 12.dp,
            shape = RoundedCornerShape(cornerRadius),
            clip = false,
            ambientColor = Color.Black.copy(alpha = 0.1f),
            spotColor = Color.Black.copy(alpha = 0.2f)
        )
        .clip(RoundedCornerShape(cornerRadius))
        .drawBehind {
            drawRoundRect(
                color = glassColor.copy(alpha = effectiveAlpha),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius.toPx())
            )
            
            drawRoundRect(
                color = borderColor,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius.toPx()),
                style = Stroke(width = 1.dp.toPx())
            )
        }
        .padding(16.dp)
}

@Composable
fun Modifier.pillGlass(
    isDark: Boolean = isSystemInDarkTheme(),
    isSelected: Boolean = false,
    alpha: Float? = null
): Modifier {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val color = if (isSelected) {
        PrimaryBlue
    } else {
        val baseColor = if (isDark) surfaceColor else Color.White
        val effectiveAlpha = alpha ?: (if (isDark) 0.3f else 0.5f)
        baseColor.copy(alpha = effectiveAlpha)
    }
    
    val borderColor = if (isDark) Color.White.copy(alpha = 0.15f) else Color(0xFFCBD5E1)

    return this
        .clip(RoundedCornerShape(50))
        .drawBehind {
            drawRoundRect(
                color = color,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.height / 2)
            )
            
            drawRoundRect(
                color = borderColor,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.height / 2),
                style = Stroke(width = 1.dp.toPx())
            )
        }
        .padding(horizontal = 20.dp, vertical = 10.dp)
}

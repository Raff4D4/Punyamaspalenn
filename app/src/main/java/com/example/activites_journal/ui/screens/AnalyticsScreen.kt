package com.example.activites_journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activites_journal.ui.theme.PrimaryBlue
import com.example.activites_journal.ui.theme.SuccessGreen
import com.example.activites_journal.utils.glassBackground
import com.example.activites_journal.utils.glassCard
import com.example.activites_journal.viewmodels.HabitViewModel
import com.example.activites_journal.viewmodels.JournalViewModel
import java.util.Locale

@Composable
fun AnalyticsScreen(
    habitViewModel: HabitViewModel,
    journalViewModel: JournalViewModel
) {
    val habits by habitViewModel.habits.collectAsState()
    val todayLogs by habitViewModel.todayLogs.collectAsState()
    val history by journalViewModel.history.collectAsState()

    val completionRate = if (habits.isNotEmpty()) {
        (todayLogs.size.toFloat() / habits.size.toFloat()) * 100
    } else 0f

    val avgMood = if (history.isNotEmpty()) {
        history.map { it.moodScale }.average()
    } else 0.0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .glassBackground()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Analytics",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .glassCard(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Completion",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${completionRate.toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .glassCard(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Avg Mood",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = String.format(Locale.getDefault(), "%.1f", avgMood),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = SuccessGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Journal History",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                items(history.reversed()) { log ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .glassCard()
                    ) {
                        // Top Row: Date and Emoji
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = log.date,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = when(log.moodScale) {
                                    1 -> "😡"
                                    2 -> "😕"
                                    3 -> "😐"
                                    4 -> "🙂"
                                    5 -> "😄"
                                    else -> "😐"
                                },
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        // MANDATORY FIX: Render Reflection Text if available
                        if (!log.reflectionText.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = log.reflectionText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

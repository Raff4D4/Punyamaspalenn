package com.example.activites_journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.activites_journal.ui.theme.PrimaryBlue
import com.example.activites_journal.ui.theme.SuccessGreen
import com.example.activites_journal.ui.theme.WarningOrange
import com.example.activites_journal.utils.glassBackground
import com.example.activites_journal.utils.glassCard
import com.example.activites_journal.utils.pillGlass
import com.example.activites_journal.viewmodels.HabitSortOrder
import com.example.activites_journal.viewmodels.HabitViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HabitTrackerScreen(
    viewModel: HabitViewModel
) {
    val habits by viewModel.habits.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()
    
    var showAddDialog by remember { mutableStateOf(false) }

    val displayDateFormat = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
    val dateHeader = displayDateFormat.format(Date())

    if (showAddDialog) {
        AddHabitDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, target ->
                viewModel.addHabit(name, target)
                showAddDialog = false
            }
        )
    }

    Scaffold(
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .glassBackground()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = dateHeader,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Sort Bar
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(HabitSortOrder.values()) { order ->
                        val isSelected = sortOrder == order
                        Box(
                            modifier = Modifier
                                .pillGlass(isSelected = isSelected)
                                .then(
                                    if (!isSelected) Modifier.border(
                                        1.dp,
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                                        RoundedCornerShape(50)
                                    ) else Modifier
                                )
                                .clickable { viewModel.setSortOrder(order) }
                        ) {
                            Text(
                                text = when (order) {
                                    HabitSortOrder.NAME_ASC -> "A-Z"
                                    HabitSortOrder.NAME_DESC -> "Z-A"
                                    HabitSortOrder.STREAK -> "Streak"
                                },
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (habits.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .glassCard()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "No habits yet",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Tap + to start your first habit!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 140.dp)
                    ) {
                        items(habits) { habitWithStreak ->
                            HabitCard(
                                habitName = habitWithStreak.habit.name,
                                target = habitWithStreak.habit.targetFrequency,
                                streak = habitWithStreak.streak,
                                isCompleted = habitWithStreak.isCompletedToday,
                                onComplete = { if (!habitWithStreak.isCompletedToday) viewModel.logHabit(habitWithStreak.habit.id) }
                            )
                        }
                    }
                }
            }

            // FloatingActionButton positioned ABOVE PillGlassNav
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 125.dp, end = 20.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Habit", modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun HabitCard(
    habitName: String,
    target: Int,
    streak: Int,
    isCompleted: Boolean,
    onComplete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .glassCard(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = habitName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Target: ${target}x/week",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "🔥 $streak Days",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = WarningOrange
                )
            }
        }
        
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(if (isCompleted) SuccessGreen else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                .clickable(enabled = !isCompleted) { onComplete() },
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(2.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), CircleShape)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("1") }

    val isSaveEnabled = name.isNotBlank() && target.isNotBlank() && (target.toIntOrNull() ?: 0) > 0

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .border(
                1.dp,
                Color.White.copy(alpha = 0.2f),
                RoundedCornerShape(28.dp)
            ),
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .glassCard(cornerRadius = 28.dp)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "New Habit",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { 
                        Text(
                            "Habit Name",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = target,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            target = newValue
                        }
                    },
                    label = { 
                        Text(
                            "Target (times per week)",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { 
                        Text("Cancel", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) 
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { 
                            val freq = target.toIntOrNull() ?: 1
                            onConfirm(name, freq) 
                        },
                        enabled = isSaveEnabled,
                        modifier = Modifier
                            .height(48.dp)
                            .width(120.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Add", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

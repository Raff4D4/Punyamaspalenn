package com.example.activites_journal.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activites_journal.ui.theme.PrimaryBlue
import com.example.activites_journal.utils.glassBackground
import com.example.activites_journal.utils.glassCard
import com.example.activites_journal.viewmodels.JournalViewModel
import kotlinx.coroutines.launch

@Composable
fun MoodJournalScreen(
    viewModel: JournalViewModel,
    snackbarHostState: SnackbarHostState
) {
    val currentEntry by viewModel.journalEntry.collectAsState()
    val scope = rememberCoroutineScope()
    
    var selectedMood by rememberSaveable { mutableIntStateOf(3) }
    var reflectionText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(currentEntry) {
        currentEntry?.let {
            if (reflectionText.isEmpty()) {
                selectedMood = it.moodScale
                reflectionText = it.reflectionText
            }
        }
    }

    val moods = listOf(
        1 to "😡",
        2 to "😕",
        3 to "😐",
        4 to "🙂",
        5 to "😄"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .glassBackground()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp).let { 
                PaddingValues(
                    start = it.calculateStartPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
                    top = it.calculateTopPadding(),
                    end = it.calculateEndPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
                    bottom = 120.dp
                )
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "How are you feeling today?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    moods.forEach { (moodValue, emoji) ->
                        val isSelected = selectedMood == moodValue
                        val scale by animateFloatAsState(if (isSelected) 1.2f else 1f)
                        
                        Box(
                            modifier = Modifier
                                .scale(scale)
                                .glassCard(
                                    cornerRadius = 20.dp, 
                                    alpha = if (isSelected) 0.65f else 0.15f,
                                    borderAlpha = if (isSelected) 1.0f else 0.2f
                                )
                                .clickable { selectedMood = moodValue },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 36.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }

            item {
                OutlinedTextField(
                    value = reflectionText,
                    onValueChange = { reflectionText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                        .glassCard(cornerRadius = 24.dp),
                    placeholder = { Text("Write your reflections here...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Button(
                    onClick = { 
                        viewModel.upsertJournal(selectedMood, reflectionText)
                        scope.launch {
                            snackbarHostState.showSnackbar("Catatan harian berhasil disimpan!")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Save Entry", color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

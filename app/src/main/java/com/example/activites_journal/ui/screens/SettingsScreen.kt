package com.example.activites_journal.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.activites_journal.ui.theme.PrimaryBlue
import com.example.activites_journal.ui.theme.SuccessGreen
import com.example.activites_journal.ui.theme.ErrorRed
import com.example.activites_journal.utils.glassBackground
import com.example.activites_journal.utils.glassCard
import com.example.activites_journal.utils.pillGlass
import com.example.activites_journal.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onLogout: () -> Unit
) {
    val themeMode by viewModel.themeMode.collectAsState()
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            onLogout()
        }
    }

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
            }
        ) {
            item {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // Appearance Toggle
            item {
                Text(
                    text = "Appearance",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .glassCard(cornerRadius = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("System", "Light", "Dark").forEach { theme ->
                        val isSelected = themeMode == theme
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .pillGlass(isSelected = isSelected)
                                .clickable { viewModel.setThemeMode(theme) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = theme,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // User Info Section
            item {
                Text(
                    text = "Account",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Username Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .glassCard(cornerRadius = 24.dp)
                            .border(1.dp, SuccessGreen.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Username",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = username ?: "User",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    
                    // Email Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .glassCard(cornerRadius = 24.dp)
                            .border(1.dp, PrimaryBlue.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Email Address",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = email ?: "user@example.com",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = { viewModel.logout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Logout", color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

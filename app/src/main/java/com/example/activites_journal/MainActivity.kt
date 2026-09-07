package com.example.activites_journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.activites_journal.ui.ViewModelFactory
import com.example.activites_journal.ui.components.PillGlassNav
import com.example.activites_journal.ui.navigation.NavGraph
import com.example.activites_journal.ui.theme.ActivitesJournalTheme
import com.example.activites_journal.utils.glassCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val app = application as HabitJournalApp
            val navController = rememberNavController()
            val viewModelFactory = ViewModelFactory(app.database.appDao(), app.sessionManager)
            
            val authViewModel: com.example.activites_journal.viewmodels.AuthViewModel = viewModel(factory = viewModelFactory)
            val habitViewModel: com.example.activites_journal.viewmodels.HabitViewModel = viewModel(factory = viewModelFactory)
            val journalViewModel: com.example.activites_journal.viewmodels.JournalViewModel = viewModel(factory = viewModelFactory)
            val settingsViewModel: com.example.activites_journal.viewmodels.SettingsViewModel = viewModel(factory = viewModelFactory)
            
            val snackbarHostState = remember { SnackbarHostState() }
            val isLoggedIn by app.sessionManager.isLoggedIn.collectAsState(initial = null)
            val themeMode by settingsViewModel.themeMode.collectAsState()
            
            val isDark = when (themeMode) {
                "Light" -> false
                "Dark" -> true
                else -> isSystemInDarkTheme()
            }

            ActivitesJournalTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn != null) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        val showBottomBar = currentRoute !in listOf("login", "register", null)
                        
                        Box(modifier = Modifier.fillMaxSize()) {
                            Scaffold(
                                containerColor = Color.Transparent,
                                contentWindowInsets = WindowInsets(0, 0, 0, 0)
                            ) { innerPadding ->
                                Box(modifier = Modifier.fillMaxSize()) {
                                    NavGraph(
                                        navController = navController,
                                        authViewModel = authViewModel,
                                        habitViewModel = habitViewModel,
                                        journalViewModel = journalViewModel,
                                        settingsViewModel = settingsViewModel,
                                        snackbarHostState = snackbarHostState,
                                        startDestination = if (isLoggedIn == true) "habit_tracker" else "login",
                                        modifier = Modifier.padding(innerPadding)
                                    )

                                    // Floating Snackbar at TopCenter
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 48.dp),
                                        contentAlignment = Alignment.TopCenter
                                    ) {
                                        SnackbarHost(hostState = snackbarHostState) { data ->
                                            Surface(
                                                modifier = Modifier
                                                    .padding(horizontal = 24.dp)
                                                    .glassCard(
                                                        cornerRadius = 50.dp,
                                                        alpha = if (isDark) 0.65f else 0.85f,
                                                        borderAlpha = 1f
                                                    ),
                                                color = Color.Transparent
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = data.visuals.message,
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    
                                    if (showBottomBar) {
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                                .windowInsetsPadding(WindowInsets.navigationBars)
                                                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                                                .fillMaxWidth(),
                                            contentAlignment = Alignment.BottomCenter
                                        ) {
                                            PillGlassNav(
                                                currentRoute = currentRoute,
                                                onNavigate = { route ->
                                                    navController.navigate(route) {
                                                        popUpTo(navController.graph.startDestinationId) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

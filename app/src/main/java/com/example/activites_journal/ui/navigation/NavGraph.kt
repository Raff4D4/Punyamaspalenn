package com.example.activites_journal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.activites_journal.ui.screens.*
import com.example.activites_journal.viewmodels.AuthViewModel
import com.example.activites_journal.viewmodels.HabitViewModel
import com.example.activites_journal.viewmodels.JournalViewModel
import com.example.activites_journal.viewmodels.SettingsViewModel

import androidx.compose.material3.SnackbarHostState

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    habitViewModel: HabitViewModel,
    journalViewModel: JournalViewModel,
    settingsViewModel: SettingsViewModel,
    snackbarHostState: SnackbarHostState,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { navController.navigate("habit_tracker") { popUpTo("login") { inclusive = true } } }
            )
        }
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = { navController.navigate("habit_tracker") { popUpTo("login") { inclusive = true } } }
            )
        }
        composable("habit_tracker") {
            HabitTrackerScreen(
                viewModel = habitViewModel
            )
        }
        composable("mood_journal") {
            MoodJournalScreen(
                viewModel = journalViewModel,
                snackbarHostState = snackbarHostState
            )
        }
        composable("analytics") {
            AnalyticsScreen(
                habitViewModel = habitViewModel,
                journalViewModel = journalViewModel
            )
        }
        composable("settings") {
            SettingsScreen(
                viewModel = settingsViewModel,
                onLogout = { navController.navigate("login") { popUpTo(0) } }
            )
        }
    }
}

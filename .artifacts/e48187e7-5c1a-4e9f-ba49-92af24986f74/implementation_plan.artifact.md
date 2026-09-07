# Implementation Plan - POS Alignment and Bug Fixes

Align all Glassmorphism modifiers, layout structures, Scaffold insets, and Form states in "Habit & Mood Journal" with the POS project patterns to eliminate visual glitches and functional bugs.

## Proposed Changes

### 1. Navigation & Insets Alignment
- **[MODIFY] [MainActivity.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/MainActivity.kt)**:
    - Move `BottomDock` (to be renamed `PillGlassNav`) out of `Scaffold.bottomBar`.
    - Place it in a `Box` with `Alignment.BottomCenter` and `padding(bottom = 24.dp)`.
    - Ensure `WindowInsets.navigationBars` are handled.
- **[NEW] [PillGlassNav.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/components/PillGlassNav.kt)**: Rename `BottomDock.kt` and update styling.
- **[DELETE] [BottomDock.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/components/BottomDock.kt)**

### 2. Settings Account Section Fix
- **[MODIFY] [SettingsScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/SettingsScreen.kt)**:
    - Re-structure Account section with clean `Column` layouts.
    - Add specific `glassCard` borders (`#34C759` for Username, `#007AFF` for Email).
    - Fix typography and spacing.

### 3. Habit Streak Logic
- **[MODIFY] [HabitViewModel.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/viewmodels/HabitViewModel.kt)**:
    - Implement streak calculation logic in the `habits` Flow.
    - Ensure streak is recalculated immediately on log insertion.
- **[MODIFY] [HabitTrackerScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/HabitTrackerScreen.kt)**:
    - Pass the calculated streak to `HabitCard`.

### 4. Mood Journal Interaction & Form Retention
- **[MODIFY] [MoodJournalScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/MoodJournalScreen.kt)**:
    - Replace emoji selection overlays with outer glowing borders and scale effects.
    - Add Snackbar feedback upon saving entries.

## Verification Plan

### Manual Verification
- Verify navigation bar floats cleanly without background leaks.
- Verify Settings screen labels and borders.
- Check a habit and verify the streak increases to at least 1.
- Save a mood entry and verify the Snackbar feedback and interaction effects.

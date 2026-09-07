# Glassmorphism UI/UX Overhaul Implementation Plan

Refine the "Habit & Mood Journal" app with a premium Apple-style Glassmorphism aesthetic, focusing on adaptivity, contrast, and interactive components.

## Proposed Changes

### 1. Foundation & Theme

#### [MODIFY] [GlassmorphismUtils.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/utils/GlassmorphismUtils.kt)
- Update `glassCard` to accept `isDark` parameter for adaptive translucency:
    - Dark: `0.10f`, Light: `0.65f`.
    - Border adaptivity: `0.18f` (Dark), `0.40f` (Light).
- Enhance `pillGlass` with high backdrop translucency and proper horizontal padding.

#### [MODIFY] [Theme.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/theme/Theme.kt)
- Introduce ambient gradients for backgrounds (Dark Slate Blue for Dark, Soft Pastel for Light).
- Ensure high contrast for text layers.

---

### 2. Auth Screens

#### [MODIFY] [LoginScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/LoginScreen.kt)
- **Headers:** "Welcome Back" / "Sign in to continue".
- **Fields:** Leading icons (`Person`, `Lock`), Trailing `Visibility` toggle for password.
- **Feedback:** Indonesian error message "Username/Email atau Password salah".

#### [MODIFY] [RegisterScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/RegisterScreen.kt)
- **Headers:** "Create Account" / "Join us today".
- **Fields:** Leading icons (`Person`, `Email`, `Lock`), Trailing `Visibility` toggle.

---

### 3. Feature Screens

#### [MODIFY] [HabitTrackerScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/HabitTrackerScreen.kt)
- **Header:** Today's date with clean formatting.
- **FAB:** Glassmorphic (+) button.
- **Cards:** Custom Glassmorphic design with `🔥 X Days` streak and interactive circular Glassmorphic Checkbox.
- **Empty State:** Friendly Glassmorphic placeholder card.

#### [MODIFY] [MoodJournalScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/MoodJournalScreen.kt)
- **Selector:** 5 large interactive Glassmorphic emoji cards with glowing `#007AFF` border for selection.
- **Input:** Translucent `OutlinedTextField` (16.dp corners, min-height 140.dp).

#### [MODIFY] [AnalyticsScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/AnalyticsScreen.kt)
- **KPIs:** Side-by-side cards with `#007AFF` and `#34C759` accents.
- **History:** Daily logs with date badges and mood emojis in refined cards.

#### [MODIFY] [SettingsScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/SettingsScreen.kt)
- **Layout:** Appearance tab at top, Account section with colored borders (`#34C759` for Username, `#007AFF` for Email), Logout at bottom.

## Verification Plan

### Manual Verification
- Deploy to emulator/device to verify Light/Dark mode transitions.
- Interact with Habit checkboxes and Mood emoji cards to ensure visual feedback (glow/highlights).
- Check contrast levels for text on Glassmorphic backgrounds.

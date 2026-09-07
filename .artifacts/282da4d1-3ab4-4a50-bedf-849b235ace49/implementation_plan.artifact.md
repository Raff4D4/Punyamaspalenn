# Radical UI/UX Overhaul: Habit & Mood Journal

This plan outlines a comprehensive redesign of the app's UI/UX to address layout issues, contrast problems, and overall aesthetic dullness. We are moving towards a vibrant, modern glassmorphic design with dynamic themes and improved navigation.

## User Review Required

> [!IMPORTANT]
> The navigation bar will be changed to a floating pill dock. This might take up some screen space at the bottom, so all scrollable screens will have increased bottom padding (120dp) to prevent content overlap.

## Proposed Changes

### Theme & Styling

#### [MODIFY] [Color.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/theme/Color.kt)
- Define new palette colors: Light Mode Slates and Dark Mode Slates.
- Update primary, secondary, and tertiary accents.

#### [MODIFY] [Theme.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/theme/Theme.kt)
- Update `LightColorScheme` and `DarkColorScheme` with specified colors.
- Set background, surface, and text colors for high contrast.

#### [MODIFY] [GlassmorphismUtils.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/utils/GlassmorphismUtils.kt)
- Update `glassBackground` to use the new Light/Dark vibrant gradients.
- Refine `glassCard` and `pillGlass` modifiers for better transparency and crisp borders.

### Components

#### [MODIFY] [PillGlassNav.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/components/PillGlassNav.kt)
- Implement a floating pill container.
- Design a prominent solid capsule for the active tab (Primary Blue, Pure White content).
- Simplify inactive tabs to subtle icons.

### Screens

#### [MODIFY] [SettingsScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/SettingsScreen.kt)
- Use `LazyColumn` for the entire layout.
- Add `120.dp` bottom padding.
- Redesign Account section with Green (Username) and Blue (Email) accent borders.
- Place Logout button at the end of the scrollable list.

#### [MODIFY] [MoodJournalScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/MoodJournalScreen.kt)
- Refactor Emoji selector to use interactive cards with glowing borders and scaling.
- Wrap content in a `LazyColumn` with `120.dp` bottom padding.

#### [MODIFY] [HabitTrackerScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/HabitTrackerScreen.kt)
- Add `120.dp` bottom padding to the `LazyColumn`.

#### [MODIFY] [AnalyticsScreen.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/ui/screens/AnalyticsScreen.kt)
- Add `120.dp` bottom padding to the `LazyColumn`.

### Main Entry

#### [MODIFY] [MainActivity.kt](file:///C:/Users/Raffa/AndroidStudioProjects/Activites_Journal/app/src/main/java/com/example/activites_journal/MainActivity.kt)
- Move `SnackbarHost` to `TopCenter`.
- Reposition it with `padding(top = 48.dp)`.
- Style Snackbar as a high-contrast Glassmorphic capsule.

## Verification Plan

### Automated Tests
- Run `gradle build` to ensure all UI changes compile correctly.

### Manual Verification
- Deploy the app to an emulator or device.
- Verify the floating nav dock remains visible and functional.
- Check light/dark mode transitions for correct gradients and contrast.
- Verify settings screen scrolling and logout button position.
- Confirm Snackbar appears at the top and is legible.
- Interact with the Emoji selector to verify scaling and border effects.
- Complete a habit and verify the streak increments to "1 Day" immediately.

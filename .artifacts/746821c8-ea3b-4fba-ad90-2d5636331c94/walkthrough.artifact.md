# Habit & Mood Journal - Implementation Walkthrough

I have successfully built the **Habit & Mood Journal** application from scratch, following the MVVM architecture and the requested Glassmorphism design system.

## Key Features Implemented

### 1. Data Layer & Persistence
- **Room Database**: Configured with four entities: `UserEntity`, `HabitEntity`, `HabitLogEntity`, and `DailyJournalEntity`.
- **Relationships**: Implemented using Foreign Keys with `CASCADE` delete rules to ensure data integrity.
- **DAO**: Comprehensive `AppDao` for authentication, habit management, and daily mood/reflection logging.
- **Session Management**: Built a `SessionManager` using **DataStore Preferences** to persist login states and user IDs across app launches.

### 2. Glassmorphism Design System
- **Custom Modifiers**: Created `glassCard()` and `pillGlass()` in `GlassmorphismUtils.kt` to provide consistent frosted-glass effects, borders, and shadows.
- **Adaptive Theme**: The app uses a dark gradient background (`GlassBgStart` to `GlassBgEnd`) to enhance the translucency of the glass components.
- **Floating Bottom Dock**: A custom navigation bar that follows the glassmorphic style, providing quick access to Tracker, Journal, Stats, and Settings.

### 3. MVVM Architecture
- **ViewModels**:
    - `AuthViewModel`: Manages registration, login, and session clearing.
    - `HabitViewModel`: Handles habit CRUD, daily check-ins, and sorting logic (A-Z, Z-A, Streak).
    - `JournalViewModel`: Manages daily mood selection (1-5 scale) and reflection text entries.
- **StateFlow**: UI state is reactive and lifecycle-aware, ensuring smooth updates when data changes in the database.

### 4. Navigation Flow
- **Startup Check**: The app automatically detects if a user is logged in.
    - **Authenticated**: Starts at the Habit Tracker.
    - **Unauthenticated**: Starts at the Login Screen.
- **Deep Navigation**: Full integration of Jetpack Compose Navigation for seamless transitions between all 6 core screens.

## Screens Overview

1.  **Login/Register**: Secure entry points with glassmorphic cards and polished input fields.
2.  **Habit Tracker**: Track daily streaks with a simple toggle interface and date-based headers.
3.  **Mood Journal**: Interactive emoji selector for quick mood tracking + reflection space.
4.  **Analytics**: Statistical overview of completion rates and mood trends over time.
5.  **Settings**: Theme preference management and account logout functionality.

## Verification
- **Build**: Successfully assembled the debug APK.
- **Deployment**: Verified the app launches to the Login screen on the emulator with the expected glassmorphic styling.

# Habit Builder Android Application

Habit Builder is a comprehensive Android application designed to help users establish and maintain daily habits through persistence tracking and streak management.

## Features

### 1. Daily Habit Tracking
- **Date-Based Completion**: Logic to track habit completion on a per-day basis using the system calendar.
- **One-Time Check-In**: Users can only complete a habit once per day. The status is persisted and the UI reflects this by disabling the completion checkbox until the next day.
- **Persistent Storage**: All completion data and habit details are stored locally using a SQLite database.

### 2. Streak Management
- **Consecutive Increments**: If a habit is completed on consecutive days, the streak count is automatically incremented.
- **Automatic Resets**: If a user misses a day, the streak count is reset to 0 upon opening the application or viewing the habit list.
- **Visual Feedback**: Active streaks are highlighted with a 🔥 icon and dynamic text coloring (Green -> Orange -> Success Green) based on the streak length. 
- **Micro-Animations**: A scaling "pop" effect provides instant satisfaction when a habit is checked off. 
- **Persistence Tracking**: The UI updates instantly and synchronizes with the local database to ensure zero data loss.
- **Responsive Layouts**: Modern, clean UI built with `LinearLayout`, `ConstraintLayout`, and `RecyclerView`.
- **Splash Screen**: A branded entry point that transitions into the home dashboard.

## Technical Implementation Details

### Database Schema (`HabitDatabaseHelper`)
The app uses a SQLite database with the following table structure for habits:
- `id`: Primary key (Integer).
- `habit_name`: Name of the habit (Text).
- `created_date`: Date the habit was first created (Text).
- `streak_count`: Current consecutive completion count (Integer).
- `last_completed_date`: The date when the habit was last checked in (Text).

### Key Components

- **`HabitAdapter.kt`**: Manages the display of habit items and handles the logic for user interactions (e.g., checking in). It calculates streak changes based on the difference between "today" and the "last completed date".
- **`HabitListActivity.kt`**: Responsible for loading habits from the database and applying the streak reset logic if the current date has progressed more than one day past the last completion.
- **`common_toolbar.xml`**: A reusable layout component shared across all activities to provide a consistent navigation experience.

## How to Run

1. Open the project in **Android Studio**.
2. Connect an Android device or start an emulator.
3. Click **Run 'app'** (`Shift + F10`).
4. Navigate through the screens:
   - **Splash**: Initial branding.
   - **Home**: Main dashboard to add or view habits.
   - **Form**: Enter a new habit name.
   - **Habit List**: View your habits, check them off, and monitor your streaks.

## Development Stack
- **Language**: Kotlin
- **Persistence**: SQLite (via `SQLiteOpenHelper`)
- **UI Framework**: Android View System (XML)
- **Navigation**: Activity-based with manual Back Button handling (`onSupportNavigateUp`)

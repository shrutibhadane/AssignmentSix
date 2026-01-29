package com.habit_builder.assignment_three

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HabitDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "HabitBuilder.db"
        private const val DATABASE_VERSION = 2

        // Table Name
        private const val TABLE_HABITS = "habits"

        // Column Names
        private const val COLUMN_ID = "id"
        private const val COLUMN_HABIT_NAME = "habit_name"
        private const val COLUMN_CREATED_DATE = "created_date"
        private const val COLUMN_STREAK_COUNT = "streak_count"
        private const val COLUMN_LAST_COMPLETED_DATE = "last_completed_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_HABITS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_HABIT_NAME TEXT, "
                + "$COLUMN_CREATED_DATE TEXT, "
                + "$COLUMN_STREAK_COUNT INTEGER, "
                + "$COLUMN_LAST_COMPLETED_DATE TEXT)")
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HABITS")
        onCreate(db)
    }

    // Insert Method
    fun insertHabit(habitName: String, createdDate: String, streakCount: Int, lastCompletedDate: String? = null): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_HABIT_NAME, habitName)
            put(COLUMN_CREATED_DATE, createdDate)
            put(COLUMN_STREAK_COUNT, streakCount)
            put(COLUMN_LAST_COMPLETED_DATE, lastCompletedDate)
        }
        return db.insert(TABLE_HABITS, null, values)
    }

    // Retrieve All Method
    fun getAllHabits(): List<Habit> {
        val habitList = mutableListOf<Habit>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HABITS", null)

        if (cursor.moveToFirst()) {
            do {
                val habit = Habit(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HABIT_NAME)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_DATE)),
                    streak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STREAK_COUNT)),
                    lastCompletedDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_COMPLETED_DATE))
                )
                habitList.add(habit)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return habitList
    }

    // Update Streak Method
    fun updateStreak(habitId: Int, newStreak: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STREAK_COUNT, newStreak)
        }
        return db.update(TABLE_HABITS, values, "$COLUMN_ID = ?", arrayOf(habitId.toString()))
    }

    // Update Completion Method
    fun updateHabitCompletion(habitId: Int, newStreak: Int, lastCompletedDate: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STREAK_COUNT, newStreak)
            put(COLUMN_LAST_COMPLETED_DATE, lastCompletedDate)
        }
        return db.update(TABLE_HABITS, values, "$COLUMN_ID = ?", arrayOf(habitId.toString()))
    }

    // Delete Method
    fun deleteHabit(habitId: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_HABITS, "$COLUMN_ID = ?", arrayOf(habitId.toString()))
    }
}

package com.habit_builder.assignment_four

import com.habit_builder.assignmentsix.R
import androidx.appcompat.widget.Toolbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habit_builder.assignment_three.Habit
import com.habit_builder.assignment_three.HabitDatabaseHelper
import java.text.SimpleDateFormat
import java.util.*

class HabitListActivity : AppCompatActivity() {

    private lateinit var dbHelper: HabitDatabaseHelper
    private lateinit var habitAdapter: HabitAdapter
    private val habits = mutableListOf<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Habits"

        dbHelper = HabitDatabaseHelper(this)
        
        // Mock data for demonstration if DB is empty
        loadHabits()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_habits)
        recyclerView.layoutManager = LinearLayoutManager(this)
        habitAdapter = HabitAdapter(habits, dbHelper)
        recyclerView.adapter = habitAdapter
    }

    private fun loadHabits() {
        var habitData = dbHelper.getAllHabits()
        if (habitData.isEmpty()) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = dateFormat.format(Date())
            
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val yesterday = dateFormat.format(calendar.time)
            
            calendar.add(Calendar.DAY_OF_YEAR, -5)
            val longAgo = dateFormat.format(calendar.time)

            // Seed some data for verification
            dbHelper.insertHabit("Yesterday Habit", today, 5, yesterday) // Should stay 5
            dbHelper.insertHabit("Today Habit", today, 3, today) // Should stay 3
            dbHelper.insertHabit("Long Ago Habit", today, 10, longAgo) // Should reset to 0
            dbHelper.insertHabit("New Habit", today, 0, null) // Should stay 0
            
            habitData = dbHelper.getAllHabits()
        }

        habits.clear()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = dateFormat.format(Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = dateFormat.format(calendar.time)

        for (habit in habitData) {
            val isCheckedToday = habit.lastCompletedDate == today
            val isCheckedYesterday = habit.lastCompletedDate == yesterday
            
            if (!isCheckedToday && !isCheckedYesterday) {
                if (habit.streak != 0) {
                    val updatedHabit = habit.copy(streak = 0)
                    dbHelper.updateStreak(habit.id, 0)
                    habits.add(updatedHabit)
                } else {
                    habits.add(habit)
                }
            } else {
                habits.add(habit)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

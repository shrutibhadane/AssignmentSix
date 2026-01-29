package com.habit_builder.assignment_two

import com.habit_builder.assignmentsix.R
import androidx.appcompat.widget.Toolbar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.habit_builder.assignment_three.HabitDatabaseHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Habit"

        val etHabitName = findViewById<EditText>(R.id.et_habit_name)
        val btnSaveHabit = findViewById<Button>(R.id.btn_save_habit)
        val dbHelper = HabitDatabaseHelper(this)

        btnSaveHabit.setOnClickListener {
            val habitName = etHabitName.text.toString().trim()

            if (habitName.isEmpty()) {
                etHabitName.error = "Habit name cannot be empty"
                Toast.makeText(this, "Please enter a habit name", Toast.LENGTH_SHORT).show()
            } else {
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val result = dbHelper.insertHabit(habitName, today, 0)
                
                if (result != -1L) {
                    Toast.makeText(this, "Habit '$habitName' saved successfully!", Toast.LENGTH_SHORT).show()
                    etHabitName.text.clear()
                    finish() // Optional: Close activity after saving
                } else {
                    Toast.makeText(this, "Error saving habit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
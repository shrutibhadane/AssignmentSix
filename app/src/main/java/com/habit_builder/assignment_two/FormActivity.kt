package com.habit_builder.assignment_two

import com.habit_builder.assignmentsix.R
import androidx.appcompat.widget.Toolbar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.habit_builder.assignment_three.HabitRepository
import com.habit_builder.viewmodel.FormViewModel
import com.habit_builder.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormActivity : AppCompatActivity() {

    private lateinit var viewModel: FormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_habit)

        val repository = HabitRepository(this)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[FormViewModel::class.java]

        val etHabitName = findViewById<EditText>(R.id.et_habit_name)
        val btnSaveHabit = findViewById<Button>(R.id.btn_save_habit)

        btnSaveHabit.setOnClickListener {
            val habitName = etHabitName.text.toString().trim()
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            if (viewModel.saveHabit(habitName, today)) {
                Toast.makeText(this, "Habit '$habitName' saved successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                if (habitName.isEmpty()) {
                    etHabitName.error = "Habit name cannot be empty"
                    Toast.makeText(this, "Please enter a habit name", Toast.LENGTH_SHORT).show()
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
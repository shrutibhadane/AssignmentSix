package com.habit_builder.assignment_four

import com.habit_builder.assignmentsix.R
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.habit_builder.assignment_three.Habit
import com.habit_builder.assignment_three.HabitRepository
import com.habit_builder.viewmodel.HabitListViewModel
import com.habit_builder.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class HabitListActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitListViewModel
    private lateinit var habitAdapter: HabitAdapter
    private val habits = mutableListOf<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.my_habits)

        val repository = HabitRepository(this)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HabitListViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        
        viewModel.loadHabits()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_habits)
        recyclerView.layoutManager = LinearLayoutManager(this)
        habitAdapter = HabitAdapter(habits) { habit, position ->
            viewModel.checkIn(habit.id, habit.streak, position)
        }
        recyclerView.adapter = habitAdapter
    }

    private fun observeViewModel() {
        viewModel.habits.observe(this) { updatedHabits ->
            habits.clear()
            habits.addAll(updatedHabits)
            habitAdapter.notifyDataSetChanged()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

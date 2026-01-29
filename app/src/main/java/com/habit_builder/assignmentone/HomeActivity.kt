package com.habit_builder.assignmentone

import com.habit_builder.assignmentsix.R
import androidx.appcompat.widget.Toolbar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.habit_builder.assignment_four.HabitListActivity
import com.habit_builder.assignment_two.FormActivity
import androidx.lifecycle.ViewModelProvider
import com.habit_builder.assignment_three.HabitRepository
import com.habit_builder.viewmodel.HomeViewModel
import com.habit_builder.viewmodel.ViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.home)

        val repository = HabitRepository(this)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        findViewById<Button>(R.id.btn_add_habit).setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_view_habits).setOnClickListener {
            val intent = Intent(this, HabitListActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.habit_builder.assignmentone

import com.habit_builder.assignmentsix.R

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.ViewModelProvider
import com.habit_builder.assignment_three.HabitRepository
import com.habit_builder.viewmodel.SplashViewModel
import com.habit_builder.viewmodel.ViewModelFactory

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val repository = HabitRepository(this)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]

        // Delay of 2 seconds (2000 milliseconds)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
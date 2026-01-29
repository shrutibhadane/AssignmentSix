package com.habit_builder.assignmentsix

import com.habit_builder.assignmentsix.R
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Habit"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
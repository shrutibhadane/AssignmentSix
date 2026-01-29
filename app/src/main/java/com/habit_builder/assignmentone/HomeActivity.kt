package com.habit_builder.assignmentone

import com.habit_builder.assignmentsix.R
import androidx.appcompat.widget.Toolbar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.habit_builder.assignment_four.HabitListActivity
import com.habit_builder.assignment_two.FormActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Home"

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
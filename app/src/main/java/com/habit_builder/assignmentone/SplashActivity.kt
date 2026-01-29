package com.habit_builder.assignmentone

import com.habit_builder.assignmentsix.R

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay of 2 seconds (2000 milliseconds)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Finish splash activity so it's removed from back stack
        }, 2000)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
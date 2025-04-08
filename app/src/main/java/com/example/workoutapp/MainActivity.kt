package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set button click listener
        binding.buttonLetsGo.setOnClickListener {
            // Replace MainActivity content with WorkoutListFragment
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, WorkoutListFragment())
                .addToBackStack(null)  // Allows back navigation
                .commit()
        }
    }
}

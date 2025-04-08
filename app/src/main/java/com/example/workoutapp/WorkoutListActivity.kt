package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class WorkoutListActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)

        // Enable the back button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewWorkouts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val workoutCategories = listOf(
            ExerciseCategory("Yoga", "Yoga exercises & meditation", R.drawable.yoga),
            ExerciseCategory("Chest", "Push movement exercises", R.drawable.shoulderpress),
            ExerciseCategory("Back", "Pull movement exercises", R.drawable.cable),
            ExerciseCategory("Arms", "Arm movement exercises", R.drawable.straightarm),
            ExerciseCategory("Cardio", "Cardio exercises", R.drawable.cardio),
            ExerciseCategory("Legs", "Legs workout", R.drawable.legsworkout),
            ExerciseCategory("Burning fat", "whole body workout", R.drawable.burningfat),
            ExerciseCategory("Strength", "Heavy lifting", R.drawable.strength)
        )

        val adapter = WorkoutCategoryAdapter(workoutCategories) { category ->
            val intent = Intent(this, ExerciseCategoryActivity::class.java)
            intent.putExtra("CATEGORY_NAME", category.name)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        
        // Setup bottom navigation
        bottomNavigation = findViewById(R.id.bottom_navigation)
        setupBottomNavigation()
    }
    
    // Handle back button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                R.id.navigation_favorites -> {
                    startActivity(Intent(this, FavoriteExercisesActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
} 
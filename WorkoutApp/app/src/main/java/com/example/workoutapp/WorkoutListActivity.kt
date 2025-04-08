package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WorkoutListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)

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
    }
}

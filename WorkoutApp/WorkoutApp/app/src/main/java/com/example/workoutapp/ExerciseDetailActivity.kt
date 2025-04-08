package com.example.workoutapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExerciseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_detail)

        val exerciseName = intent.getStringExtra("EXERCISE_NAME") ?: "Unknown"
        val exerciseDescription = intent.getStringExtra("EXERCISE_DESCRIPTION") ?: "No description available"
        val exerciseReps = intent.getStringExtra("EXERCISE_REPS") ?: "Reps not specified"
        val exerciseImage = intent.getIntExtra("EXERCISE_IMAGE", 0)

        val imageView: ImageView = findViewById(R.id.exerciseDetailImage)
        val nameView: TextView = findViewById(R.id.exerciseDetailName)
        val descriptionView: TextView = findViewById(R.id.exerciseDetailDescription)
        val repsView: TextView = findViewById(R.id.exerciseDetailReps)

        nameView.text = exerciseName
        descriptionView.text = exerciseDescription
        repsView.text = exerciseReps
        if (exerciseImage != 0) {
            imageView.setImageResource(exerciseImage)
        }
    }
}

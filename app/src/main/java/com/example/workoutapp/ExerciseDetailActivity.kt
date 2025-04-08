package com.example.workoutapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExerciseDetailActivity : AppCompatActivity() {
    private lateinit var exercise: Exercise
    private lateinit var favoriteButton: FloatingActionButton
    private lateinit var favoriteManager: FavoriteManager
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_detail)

        // Enable the back button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        exercise = intent.getParcelableExtra("EXERCISE") ?: throw IllegalStateException("Exercise not found")
        favoriteManager = FavoriteManager.getInstance(this)
        
        val imageView: ImageView = findViewById(R.id.exerciseDetailImage)
        val nameView: TextView = findViewById(R.id.exerciseDetailName)
        val descriptionView: TextView = findViewById(R.id.exerciseDetailDescription)
        val repsView: TextView = findViewById(R.id.exerciseDetailReps)
        favoriteButton = findViewById(R.id.favoriteButton)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        nameView.text = exercise.name
        descriptionView.text = exercise.description
        repsView.text = exercise.reps
        if (exercise.imageResId != 0) {
            imageView.setImageResource(exercise.imageResId)
        } else {
            imageView.setImageResource(R.drawable.warning)
        }

        updateFavoriteButton()
        favoriteButton.setOnClickListener {
            if (favoriteManager.isFavorite(exercise)) {
                favoriteManager.removeFavorite(exercise)
            } else {
                favoriteManager.addFavorite(exercise)
            }
            updateFavoriteButton()
        }

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

    private fun updateFavoriteButton() {
        favoriteButton.setImageResource(
            if (favoriteManager.isFavorite(exercise)) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )
    }

    companion object {
        fun newIntent(context: Context, exercise: Exercise): Intent {
            return Intent(context, ExerciseDetailActivity::class.java).apply {
                putExtra("EXERCISE", exercise)
            }
        }
    }
}

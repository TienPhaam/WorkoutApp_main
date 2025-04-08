package com.example.workoutapp

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ActivityFavoriteExercisesBinding
import com.google.android.material.snackbar.Snackbar

class FavoriteExercisesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteExercisesBinding
    private lateinit var adapter: ExerciseAdapter
    private lateinit var favoriteManager: FavoriteManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable the back button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        favoriteManager = FavoriteManager.getInstance(this)
        setupRecyclerView()
        setupSwipeToDelete()
        loadFavoriteExercises()
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

    private fun setupRecyclerView() {
        adapter = ExerciseAdapter { exercise ->
            startActivity(ExerciseDetailActivity.newIntent(this, exercise))
        }
        binding.recyclerViewFavorites.apply {
            layoutManager = LinearLayoutManager(this@FavoriteExercisesActivity)
            adapter = this@FavoriteExercisesActivity.adapter
        }
    }

    private fun setupSwipeToDelete() {
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)
        val background = ColorDrawable(Color.RED)
        
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // We don't want to support moving items up/down
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val exercise = adapter.currentList[position]
                
                // Remove from favorites
                favoriteManager.removeFavorite(exercise)
                
                // Update the adapter
                loadFavoriteExercises()
                
                // Show a snackbar with undo option
                Snackbar.make(
                    binding.recyclerViewFavorites,
                    "${exercise.name} removed from favorites",
                    Snackbar.LENGTH_LONG
                ).setAction("UNDO") {
                    favoriteManager.addFavorite(exercise)
                    loadFavoriteExercises()
                }.show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                
                // Draw the red delete background
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)
                
                // Calculate position for the delete icon
                deleteIcon?.let {
                    val iconMargin = (itemView.height - it.intrinsicHeight) / 2
                    val iconTop = itemView.top + iconMargin
                    val iconBottom = iconTop + it.intrinsicHeight
                    
                    if (dX < 0) { // Swiping to the left
                        val iconLeft = itemView.right - iconMargin - it.intrinsicWidth
                        val iconRight = itemView.right - iconMargin
                        it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    } else { // Swiping to the right
                        val iconLeft = itemView.left + iconMargin
                        val iconRight = itemView.left + iconMargin + it.intrinsicWidth
                        it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    }
                    
                    it.draw(c)
                }
                
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewFavorites)
    }

    private fun loadFavoriteExercises() {
        val favoriteExercises = favoriteManager.getFavorites()
        adapter.submitList(favoriteExercises)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.navigation_favorites
        
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                R.id.navigation_favorites -> {
                    // Already at favorites, do nothing
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteExercises()
    }
} 
package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.FragmentWorkoutListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class WorkoutListFragment : Fragment() {

    private var _binding: FragmentWorkoutListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workoutCategories = listOf(
            ExerciseCategory(
                name = "Yoga",
                description = "Yoga exercises & meditation",
                imageResId = R.drawable.yoga
            ),
            ExerciseCategory(
                name = "Chest",
                description = "Push movement exercises",
                imageResId = R.drawable.shoulderpress
            ),
            ExerciseCategory(
                name = "Back",
                description = "Pull movement exercises",
                imageResId = R.drawable.cable
            ),
            ExerciseCategory(
                name = "Arms",
                description = "Arm movement exercises",
                imageResId = R.drawable.straightarm
            ),
            ExerciseCategory(
                name = "Cardio",
                description = "Cardio exercises",
                imageResId = R.drawable.cardio
            ),
            ExerciseCategory(
                name = "Legs",
                description = "Legs workout",
                imageResId = R.drawable.legsworkout
            )
        )

        val adapter = WorkoutCategoryAdapter(workoutCategories) { category ->
            val intent = Intent(requireContext(), ExerciseCategoryActivity::class.java)
            intent.putExtra("CATEGORY_NAME", category.name ?: "Default Category")
            startActivity(intent)
        }

        binding.recyclerViewWorkouts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewWorkouts.adapter = adapter
        
        setupBottomNavigation()
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Go back to main activity
                    requireActivity().finish()
                    true
                }
                R.id.navigation_favorites -> {
                    startActivity(Intent(requireContext(), FavoriteExercisesActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

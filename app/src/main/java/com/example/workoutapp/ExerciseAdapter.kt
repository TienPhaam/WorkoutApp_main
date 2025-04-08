package com.example.workoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ExerciseAdapter(
    private val onItemClick: (Exercise) -> Unit
) : ListAdapter<Exercise, ExerciseAdapter.ViewHolder>(ExerciseDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseImage: ImageView = view.findViewById(R.id.exerciseImage)
        val exerciseName: TextView = view.findViewById(R.id.exerciseName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.exerciseName.text = exercise.name

        // Use Glide for efficient image loading and prevent memory issues
        Glide.with(holder.itemView.context)
            .load(exercise.imageResId)
            .override(300, 200) // Resize image to avoid large memory usage
            .placeholder(R.drawable.giphy) // Show placeholder while loading
            .error(R.drawable.giphy) // Show error image if loading fails
            .into(holder.exerciseImage)

        holder.itemView.setOnClickListener { onItemClick(exercise) }
    }
}

private class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem == newItem
    }
}
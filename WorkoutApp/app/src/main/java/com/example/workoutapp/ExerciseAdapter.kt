package com.example.workoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val onItemClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

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
        val exercise = exercises[position]
        holder.exerciseName.text = exercise.name
        holder.exerciseImage.setImageResource(exercise.imageResId)

        holder.itemView.setOnClickListener { onItemClick(exercise) }
    }

    override fun getItemCount() = exercises.size
}

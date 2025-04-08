package com.example.workoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutCategoryAdapter(
    private val categoryList: List<ExerciseCategory>,
    private val onItemClick: (ExerciseCategory) -> Unit
) : RecyclerView.Adapter<WorkoutCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryImage: ImageView = view.findViewById(R.id.workoutImage)
        val categoryName: TextView = view.findViewById(R.id.workoutName)
        val categoryDescription: TextView = view.findViewById(R.id.workoutDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.name
        holder.categoryDescription.text = category.description
        holder.categoryImage.setImageResource(category.imageResId)

        // Click event
        holder.itemView.setOnClickListener { onItemClick(category) }
    }

    override fun getItemCount() = categoryList.size
}

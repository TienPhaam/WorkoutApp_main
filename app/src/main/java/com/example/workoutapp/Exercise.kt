package com.example.workoutapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val name: String,
    val description: String,
    val imageResId: Int,
    val reps: String,
    var isFavorite: Boolean = false
) : Parcelable

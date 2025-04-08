package com.example.workoutapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addFavorite(exercise: Exercise) {
        val favorites = getFavorites().toMutableList()
        if (!favorites.any { it.name == exercise.name }) {
            favorites.add(exercise)
            saveFavorites(favorites)
        }
    }

    fun removeFavorite(exercise: Exercise) {
        val favorites = getFavorites().toMutableList()
        favorites.removeAll { it.name == exercise.name }
        saveFavorites(favorites)
    }

    fun isFavorite(exercise: Exercise): Boolean {
        return getFavorites().any { it.name == exercise.name }
    }

    fun getFavorites(): List<Exercise> {
        val json = sharedPreferences.getString(KEY_FAVORITES, "[]")
        val type = object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveFavorites(favorites: List<Exercise>) {
        val json = gson.toJson(favorites)
        sharedPreferences.edit().putString(KEY_FAVORITES, json).apply()
    }

    companion object {
        private const val PREFS_NAME = "FavoriteExercises"
        private const val KEY_FAVORITES = "favorites"

        @Volatile
        private var instance: FavoriteManager? = null

        fun getInstance(context: Context): FavoriteManager {
            return instance ?: synchronized(this) {
                instance ?: FavoriteManager(context.applicationContext).also { instance = it }
            }
        }
    }
} 
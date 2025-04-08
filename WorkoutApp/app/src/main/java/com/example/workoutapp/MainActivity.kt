package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLetsGo: Button = findViewById(R.id.buttonLetsGo)
        buttonLetsGo.setOnClickListener {
            val intent = Intent(this, WorkoutListActivity::class.java)
            startActivity(intent)
        }
    }
}

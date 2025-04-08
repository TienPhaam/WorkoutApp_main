package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ExerciseCategoryActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_category)

        // Enable Back Button in Action Bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val categoryName = intent.getStringExtra("CATEGORY_NAME")
        val categoryTitle: TextView = findViewById(R.id.categoryTitle)
        categoryTitle.text = categoryName

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewExercises)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val exerciseList = getExercisesForCategory(categoryName)

        val adapter = ExerciseAdapter { selectedExercise ->
            startActivity(ExerciseDetailActivity.newIntent(this, selectedExercise))
        }
        recyclerView.adapter = adapter
        adapter.submitList(exerciseList)
        
        // Setup bottom navigation
        bottomNavigation = findViewById(R.id.bottom_navigation)
        setupBottomNavigation()
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

    // Handle Back Button Click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun getAllExercises(): List<Exercise> {
            return listOf(
                "Yoga", "Chest", "Back", "Arms", "Cardio", "Legs"
            ).flatMap { category -> 
                getExercisesForCategory(category)
            }
        }

        private fun getExercisesForCategory(category: String?): List<Exercise> {
            return when (category) {
                "Yoga" -> listOf(
                    Exercise("Standing yoga poses", "Standing yoga poses build strength, balance, and flexibility while improving posture and focus.\n" +
                            "\n" +
                            "1. Mountain Pose (Tadasana) – Stand tall, feet together, engage core, relax shoulders, and breathe deeply.\n" +
                            "2. Warrior I (Virabhadrasana I) – Step back, bend front knee, square hips forward, raise arms overhead.\n" +
                            "3. Warrior II (Virabhadrasana II) – Lunge position, extend arms, open hips, gaze over front hand.\n" +
                            "4. Tree Pose (Vrksasana) – Balance on one foot, place the other on the inner thigh or calf, hands in prayer.\n" +
                            "5. Chair Pose (Utkatasana) – Lower into a squat, extend arms overhead, keep knees aligned.\n" +
                            "6. Triangle Pose (Trikonasana) – Step wide, extend arms, reach one hand down, open chest, gaze up.\n" +
                            "7. Extended Side Angle (Utthita Parsvakonasana) – From Warrior II, bring front elbow to thigh or hand to floor, extend top arm.", R.drawable.standingyoga, "Beginners: 15–30 seconds (3–5 breaths)\n" +
                            "Intermediate: 30–45 seconds (5–8 breaths)\n" +
                            "Advanced: 45–60 seconds (8–10+ breaths)"),
                    Exercise("Balancing yoga poses", "1. Balancing yoga poses require focus, core strength, and steady breathing. To improve balance:\n" +
                            "\n" +
                            "2. Engage Core – Keep your abdominal muscles active.\n" +
                            "3. Fix Your Gaze – Focus on a still point (Drishti) to maintain stability.\n" +
                            "4. Even Weight Distribution – Ground through your standing foot.\n" +
                            "5. Controlled Breathing – Breathe steadily to stay centered.\n" +
                            "6. Start Simple – Practice poses like Tree Pose before advancing.", R.drawable.balance, "Beginners: Start with 15–30 seconds, gradually increasing time.\n" +
                            "Intermediate/Advanced: Hold for 30–60 seconds or longer.\n" +
                            "Repeat: Do 2–3 rounds per pose for better stability.")
                )

                "Chest" -> listOf(
                    Exercise("Bench Press", "1. Setup – Lie flat on a bench with feet firmly on the ground. Grip the bar slightly wider than shoulder-width.\n" +
                            "2. Unrack – Lift the bar off the rack and hold it above your chest with straight arms.\n" +
                            "3. Lower – Slowly bring the bar down to your mid-chest, keeping elbows at about a 75° angle.\n" +
                            "4. Press – Push the bar back up explosively until arms are fully extended.\n" +
                            "5. Repeat – Perform depending on your goal.\n" +
                            "* Tips: Keep your back slightly arched, engage your core, and control the movement.", R.drawable.bench, "4 sets of 10 reps"),
                    Exercise("Incline Press", "1. Setup – Adjust the bench to a 30-45° incline. Lie back with feet flat on the ground.\n" +
                            "2. Grip – Hold the bar slightly wider than shoulder-width, wrists straight.\n" +
                            "3. Unrack – Lift the bar off the rack and position it above your upper chest.\n" +
                            "4. Lower – Slowly bring the bar down to your upper chest (just below the collarbone).\n" +
                            "5. Press – Push the bar back up until your arms are fully extended.\n" +
                            "6. Repeat – Perform depending on your goal.", R.drawable.incline, "3 sets of 12 reps"),
                    Exercise("Cable Press", "1. Setup – Adjust the bench to a 30-45° incline. Lie back with feet flat on the ground.\n" +
                            "2. Grip – Hold the bar slightly wider than shoulder-width, wrists straight.\n" +
                            "3. Unrack – Lift the bar off the rack and position it above your upper chest.\n" +
                            "4. Lower – Slowly bring the bar down to your upper chest (just below the collarbone).\n" +
                            "5. Press – Push the bar back up until your arms are fully extended.\n" +
                            "6. Repeat – Perform depending on your goal.", R.drawable.cable, "3 sets of 12 reps")

                )
                "Back" -> listOf(
                    Exercise("Pull-ups", "1. Grip – Grab the bar with an overhand grip, slightly wider than shoulder-width.\n" +
                            "2. Hang – Engage your core and let your body hang with straight arms.\n" +
                            "3. Pull – Drive elbows down and pull your chin above the bar.\n" +
                            "4. Lower – Slowly lower yourself back to a full hang.", R.drawable.pullsup, "4 sets of 8 reps"),
                    Exercise("Deadlift", "1. Setup – Stand with feet hip-width apart, bar over midfoot. Grip the bar shoulder-width with an overhand or mixed grip.\n" +
                            "2. Position – Bend knees slightly, hinge at hips, keep back straight, and chest up.\n" +
                            "3. Lift – Drive through heels, engage core, and lift the bar by extending hips and knees.\n" +
                            "4. Lockout – Stand tall with shoulders back and hips fully extended.\n" +
                            "5. Lower – Hinge at the hips and slowly lower the bar back to the floor.", R.drawable.deadlift, "3 sets of 6 reps"),
                    Exercise("Cable Rows", "1. Setup – Sit at the cable row machine, feet on the platform, knees slightly bent.\n" +
                            "2. Grip – Hold the handle with an overhand or neutral grip.\n" +
                            "3. Posture – Keep your back straight, chest up, and core engaged.\n" +
                            "4. Pull – Drive elbows back, pulling the handle to your midsection.\n" +
                            "5. Squeeze – Hold for a second at the top, squeezing your back muscles.\n" +
                            "6. Return – Slowly extend your arms back to the starting position.", R.drawable.cablerow, "3 sets of 15 reps"),
                )
                "Arms" -> listOf(
                    Exercise("Bicep Curls", "1. Grip – Hold a dumbbell or barbell with an underhand grip, palms facing up.\n" +
                            "2. Posture – Stand tall, feet shoulder-width apart, and engage your core.\n" +
                            "3. Curl – Keep elbows close to your body and lift the weight towards your shoulders.\n" +
                            "4. Squeeze – Contract your biceps at the top, then pause briefly.\n" +
                            "5. Lower – Slowly return to the starting position with controlled movement.", R.drawable.biceps, "3 sets of 15 reps"),
                    Exercise("Triceps Dips", "1. Using Parallel Bars:\n" +
                            "2. Grip – Hold onto parallel bars with arms extended, shoulders down.\n" +
                            "3. Lower – Bend elbows and lower your body until elbows are at 90°.\n" +
                            "4. Press Up – Push back up until arms are fully extended.", R.drawable.tricep, "3 sets of 12 reps"),
                    Exercise("Forearm", "1. Wrist Curls (Flexion & Extension)\n" +
                            "2. Flexion: Hold a dumbbell, palm up, and curl your wrist upward.\n" +
                            "3. Extension: Hold a dumbbell, palm down, and curl your wrist upward.", R.drawable.forarm, "3 sets of 12 reps")
                )
                "Cardio" -> listOf(
                        Exercise("Battle Ropes", "1. Stance – Feet shoulder-width apart, knees bent, core engaged.\n" +
                                "2. Grip – Hold ropes firmly, hands shoulder-width apart.\n" +
                                "Movements –\n" +
                                "3. Alternating Waves – Move arms up and down alternately.\n" +
                                "4. Double Waves – Move both arms together.\n" +
                                "5. Slams – Lift and slam ropes forcefully.\n" +
                                "6. Circles – Move ropes in circles.\n" +
                                "7. Duration – 30–60 sec per set, 3–5 sets with short rests.", R.drawable.rope, ""),
                        Exercise("Abs Workout", "1. Crunches – 3 sets of 15 reps\n" +
                                "2. Leg Raises – 3 sets of 12 reps\n" +
                                "3. Russian Twists – 3 sets of 20 reps (10 each side)\n" +
                                "4. Plank – Hold for 30–60 sec, 3 sets\n" +
                                "5. Bicycle Crunches – 3 sets of 20 reps (10 each side)\n" +
                                "6. Hanging Knee Raises – 3 sets of 12 reps (optional)", R.drawable.abs, ""),
                                Exercise("Treadmill", "1. Warm-Up – Walk at 3–4 km/h for 5 minutes.\n" +
                                        "2. Steady-State Cardio – Jog or run at a comfortable pace for 20–40 minutes.\n" +
                                        "3. Interval Training (HIIT) –\n" +
                                        "4. Sprint 30 sec, walk 60 sec (repeat 8–12 times).\n" +
                                        "5. Incline Walk – Set incline to 5–12%, walk briskly for 15–30 min.\n" +
                                        "6. Cool-Down – Walk at 3 km/h for 5 minutes.", R.drawable.treadmill, "")
                    )
                "Legs" -> listOf(
                    Exercise("Squat", "1. Stance – Stand with feet shoulder-width apart, toes slightly out.\n" +
                            "2. Brace Core – Keep chest up, back straight, and engage your core.\n" +
                            "3. Lower – Push hips back and bend knees until thighs are parallel to the floor.\n" +
                            "4. Push Up – Drive through heels and return to standing.", R.drawable.squat, "3 sets of 15 reps"),
                    Exercise("Deadlift", "1. Setup – Stand with feet hip-width apart, bar over midfoot. Grip the bar shoulder-width with an overhand or mixed grip.\n" +
                            "2. Position – Bend knees slightly, hinge at hips, keep back straight, and chest up.\n" +
                            "3. Lift – Drive through heels, engage core, and lift the bar by extending hips and knees.\n" +
                            "4. Lockout – Stand tall with shoulders back and hips fully extended.\n" +
                            "5. Lower – Hinge at the hips and slowly lower the bar back to the floor.", R.drawable.deadlift, "3 sets of 6 reps"),
                    Exercise("Legs Extension", "1. Setup – Sit on the leg extension machine, back flat against the seat.\n" +
                            "2. Position – Place feet under the padded bar, knees at a 90° angle.\n" +
                            "3. Lift – Extend your legs until they are straight, but don't lock your knees.\n" +
                            "4. Hold – Squeeze your quads at the top for 1–2 seconds.\n" +
                            "5. Lower – Slowly bring legs back to the starting position.\n", R.drawable.legextension, "3 sets of 12 reps")
                )
                else -> emptyList()
            }
        }
    }
}


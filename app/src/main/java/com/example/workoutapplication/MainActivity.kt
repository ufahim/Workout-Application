package com.example.workoutapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var exerciseTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var completeButton: Button
    private lateinit var imageView: ImageView

    private var exerciseIndex = 0
    private lateinit var currentExercise: Exercise
    private lateinit var timer: CountDownTimer

    private val exercises = mutableListOf(
        Exercise("Push-ups", "Place your hands shoulder-width apart on the floor. Lower your body until your chest nearly touches the floor. Push your body back up until your arms are fully extended.", 30,"https://media.tenor.com/gI-8qCUEko8AAAAC/pushup.gif"),
        Exercise("Squats", "Stand with your feet shoulder-width apart. Lower your body as far as you can by pushing your hips back and bending your knees. Return to the starting position.", 45,"https://thumbs.gfycat.com/HeftyPartialGroundbeetle-size_restricted.gif"),
        Exercise("Plank", "Start in a push-up position, then bend your elbows and rest your weight on your forearms. Hold this position for as long as you can.", 60,"https://media.tenor.com/6SOetkNbfakAAAAM/plank-abs.gif")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        exerciseTextView = findViewById(R.id.exerciseTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        completeButton = findViewById(R.id.completeButton)
        imageView = findViewById(R.id.imageView)

        startButton.setOnClickListener {
            startWorkout()
        }

        completeButton.setOnClickListener {
            completeExercise()
        }
    }

    private fun completeExercise() {
        timer.cancel()
        completeButton.isEnabled = false
        startNextExercise()
    }


    private fun startWorkout() {
        exerciseIndex = 0
        titleTextView.text = "Workout Started"
        startButton.isEnabled = false
        startButton.text = "Workout In Progress"
        startNextExercise()
    }
    private fun startNextExercise() {
        if (exerciseIndex < exercises.size) {
            currentExercise = exercises[exerciseIndex]
            exerciseTextView.text = currentExercise.name
            descriptionTextView.text = currentExercise.description
            Glide.with(this@MainActivity)
                .asGif()
                .load(currentExercise.gifImageUrl)
                .into(imageView)
            timerTextView.text = formatTime(currentExercise.durationInSeconds)

            timer = object : CountDownTimer(currentExercise.durationInSeconds * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTextView.text = formatTime((millisUntilFinished / 1000).toInt())

                }

                override fun onFinish() {
                    timerTextView.text = "Exercise Complete"
                    imageView.visibility = View.VISIBLE
                    completeButton.isEnabled = true
                }
            }.start()

            exerciseIndex++
        } else {
            exerciseTextView.text = "Workout Complete"
            descriptionTextView.text = ""
            timerTextView.text = ""
            completeButton.isEnabled = false
            startButton.isEnabled = true
            startButton.text = "Start Again"
        }
    }
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

}
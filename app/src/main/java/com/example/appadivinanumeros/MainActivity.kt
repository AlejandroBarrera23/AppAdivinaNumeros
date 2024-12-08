package com.example.appadivinanumeros

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var randomNumber = 0
    var attemptsLeft = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val etGuess: EditText = findViewById(R.id.et_guess)
        val btnGuess: Button = findViewById(R.id.btn_guess)
        val tvFeedback: TextView = findViewById(R.id.tv_feedback)
        val tvAttempts: TextView = findViewById(R.id.tv_attempts)
        val btnReset: Button = findViewById(R.id.btn_reset)

        initializeGame(tvFeedback, tvAttempts)

        btnGuess.setOnClickListener {
            val guessText = etGuess.text.toString()

            if (guessText.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val guess = guessText.toInt()
            checkGuess(guess, tvFeedback, tvAttempts, btnGuess, btnReset)
        }

        btnReset.setOnClickListener {
            initializeGame(tvFeedback, tvAttempts)
            btnGuess.isEnabled = true
            btnReset.visibility = Button.GONE
            etGuess.text.clear()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun initializeGame(tvFeedback: TextView, tvAttempts: TextView) {
        randomNumber = Random.nextInt(1, 101)
        attemptsLeft = 10
        tvFeedback.text = "Adivina el numero"
        tvAttempts.text = "Intentos restantes: $attemptsLeft"
    }

    fun checkGuess(
        guess: Int,
        tvFeedback: TextView,
        tvAttempts: TextView,
        btnGuess: Button,
        btnReset: Button
    ) {
        when {
            guess == randomNumber -> {
                tvFeedback.text = "Correcto"
                btnGuess.isEnabled = false
                btnReset.visibility = Button.VISIBLE
            }

            guess > randomNumber -> {
                tvFeedback.text = "Demasiado alto. Intenta con un número más bajo."
            }

            else -> {
                tvFeedback.text = "Demasiado bajo. Intenta con un número más alto."
            }
        }

        attemptsLeft--
        tvAttempts.text = "Intentos restantes: $attemptsLeft"

        if (attemptsLeft <= 0 && guess != randomNumber) {
            tvFeedback.text = "Has perdido. El número era $randomNumber."
            btnGuess.isEnabled = false
            btnReset.visibility = Button.VISIBLE
        }
    }

}
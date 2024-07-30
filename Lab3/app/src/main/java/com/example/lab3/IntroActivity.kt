package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val welcomeText = findViewById<TextView>(R.id.welcome_text)
        val calculatorImage = findViewById<ImageView>(R.id.calculator_image)
        val goToCalculatorButton = findViewById<Button>(R.id.go_to_calculator_button)

        // Apply animations
        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        welcomeText.startAnimation(slideInAnimation)
        calculatorImage.startAnimation(slideInAnimation)

        goToCalculatorButton.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
        }
    }
}

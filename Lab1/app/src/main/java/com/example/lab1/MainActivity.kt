package com.example.lab1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_button)
        //when toggle is on, screen in landscape mode
        val orientationSwitch = findViewById<Switch>(R.id.switch1)
        orientationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { // Landscape mode enabled
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else { // Portrait mode enabled
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            if (validateInput(username, password)) {
                // Handle successful login (including "admin" case)
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HelloWorldActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 8) {
            Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        // 3. Check if username and password match expected input
        if (username != "lenageo" || password != "12345678") {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            return false
        }


        return true // All validations passed
    }


}

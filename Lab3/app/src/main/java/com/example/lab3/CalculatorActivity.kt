package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentNumber = ""
    private var operator = ""
    private var result = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        display = findViewById(R.id.display)

        // Setup buttons and their click listeners
        setupButtons()
    }

    private fun setupButtons() {
        val buttons = listOf(
            R.id.button_0 to "0", R.id.button_1 to "1", R.id.button_2 to "2",
            R.id.button_3 to "3", R.id.button_4 to "4", R.id.button_5 to "5",
            R.id.button_6 to "6", R.id.button_7 to "7", R.id.button_8 to "8",
            R.id.button_9 to "9", R.id.button_add to "+", R.id.button_subtract to "-",
            R.id.button_multiply to "×", R.id.button_divide to "/",
            R.id.button_equals to "=", R.id.button_clear to "AC",
            R.id.button_decimal to "."
        )

        for ((id, text) in buttons) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(text) }
        }
    }

    private fun onButtonClick(text: String) {
        when (text) {
            "AC" -> clear()
            "+", "-", "×", "/" -> setOperator(text)
            "=" -> calculate()
            "." -> appendDecimal()
            else -> appendNumber(text)
        }
    }

    private fun clear() {
        currentNumber = ""
        operator = ""
        result = 0.0
        display.text = "0"
    }

    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            result = currentNumber.toDouble()
            currentNumber = ""
        }
        operator = op
    }

    private fun calculate() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDouble()
            result = when (operator) {
                "+" -> result + number
                "-" -> result - number
                "×" -> result * number
                "/" -> result / number
                else -> result
            }
            display.text = result.toString()
            currentNumber = ""
            operator = ""
        }
    }

    private fun appendNumber(number: String) {
        currentNumber += number
        display.text = currentNumber
    }

    private fun appendDecimal() {
        if (!currentNumber.contains(".")) {
            currentNumber += "."
            display.text = currentNumber
        }
    }
}

package com.example.test1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class ReadWriteActivity : AppCompatActivity() {
    private lateinit var fileNameInput: EditText
    private lateinit var dataInput: EditText
    private lateinit var fileContentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_write)

        fileNameInput = findViewById(R.id.filenameInput)
        dataInput = findViewById(R.id.dataInput)
        fileContentTextView = findViewById(R.id.fileContentTextView)

        // Button to append data to the file
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val fileName = fileNameInput.text.toString()
            val data = dataInput.text.toString()
            if (fileName.isNotEmpty() && data.isNotEmpty()) {
                appendToFile(fileName, data)
            } else {
                Toast.makeText(this, "Please enter both filename and data", Toast.LENGTH_SHORT).show()
            }
        }

        // Button to read data from the file
        val readButton: Button = findViewById(R.id.readButton)
        readButton.setOnClickListener {
            val fileName = fileNameInput.text.toString()
            if (fileName.isNotEmpty()) {
                readFromFile(fileName)
            } else {
                Toast.makeText(this, "Please enter the filename", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to append data to a file
    private fun appendToFile(fileName: String, data: String) {
        val file = File(filesDir, fileName)
        file.appendText(data + "\n")
        Toast.makeText(this, "Data saved to $fileName", Toast.LENGTH_SHORT).show()
    }

    // Function to read data from a file
    private fun readFromFile(fileName: String) {
        val file = File(filesDir, fileName)
        if (file.exists()) {
            val content = file.readText()
            fileContentTextView.text = content
            Toast.makeText(this, "Data read from $fileName", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show()
        }
    }
}

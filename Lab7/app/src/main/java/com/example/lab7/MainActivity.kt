package com.example.lab7

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Popup Menu Button
        val actionButton = findViewById<Button>(R.id.action_button)
        actionButton.setOnClickListener { showPopup(it) }

        // Context Menu TextView
        val longPressTextView = findViewById<TextView>(R.id.long_press_text)
        registerForContextMenu(longPressTextView)
    }

    // Create Options Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    // Handle Options Menu Item Selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            R.id.action_help -> {
                startActivity(Intent(this, HelpActivity::class.java))
                true
            }
            R.id.action_feedback -> {
                startActivity(Intent(this, FeedbackActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Show Popup Menu
    private fun showPopup(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_delete -> {
                    Toast.makeText(this, "Delete clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_share -> {
                    Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    // Create Context Menu for Long Press on TextView
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Handle Context Menu Item Selection and link to respective activities
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_copy -> {
                startActivity(Intent(this, ProfileActivity::class.java)) // Link to ProfileActivity
                true
            }
            R.id.action_paste -> {
                startActivity(Intent(this, SettingsActivity::class.java)) // Link to SettingsActivity
                true
            }
            R.id.action_cut -> {
                startActivity(Intent(this, AboutActivity::class.java)) // Link to AboutActivity
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}

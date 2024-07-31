package com.example.activity_lifecycle

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var currentSong: Int = R.raw.sample_song // Replace with your audio file
    private var playbackPosition: Int = 0
    private lateinit var lifecycleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, currentSong)
        lifecycleTextView = findViewById(R.id.lifecycleTextView)
        logAndDisplayLifecycleEvent("onCreate called")
    }

    fun onPlayButtonClick(view: View) {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
        logAndDisplayLifecycleEvent("Play button clicked")
    }

    fun onPauseButtonClick(view: View) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            playbackPosition = mediaPlayer.currentPosition
        }
        logAndDisplayLifecycleEvent("Pause button clicked")
    }

    override fun onStart() {
        super.onStart()
        logAndDisplayLifecycleEvent("onStart called")
    }

    override fun onResume() {
        super.onResume()
        // Resume playback if there was a saved position
        if (playbackPosition > 0) {
            mediaPlayer.seekTo(playbackPosition)
            mediaPlayer.start()
        } else {
            mediaPlayer.start()
        }
        logAndDisplayLifecycleEvent("onResume called")
    }

    override fun onPause() {
        super.onPause()
        // Save the current playback position and pause the player
        playbackPosition = mediaPlayer.currentPosition
        mediaPlayer.pause()
        logAndDisplayLifecycleEvent("onPause called")
    }

    override fun onStop() {
        super.onStop()
        // Release the MediaPlayer resources if not needed anymore
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
        logAndDisplayLifecycleEvent("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        logAndDisplayLifecycleEvent("onDestroy called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current playback position
        outState.putInt("playbackPosition", playbackPosition)
        logAndDisplayLifecycleEvent("onSaveInstanceState called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the playback position
        playbackPosition = savedInstanceState.getInt("playbackPosition", 0)
        logAndDisplayLifecycleEvent("onRestoreInstanceState called")
    }

    private fun logAndDisplayLifecycleEvent(event: String) {
        Log.d("MainActivity", event)
        lifecycleTextView.append("\n$event")
    }
}

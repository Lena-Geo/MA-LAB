package com.example.activity_lifecycle

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var currentSong: Int = R.raw.sample_song // Replace with your audio file
    private var playbackPosition: Int = 0
    private lateinit var lifecycleTextView: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var currentPositionTextView: TextView
    private lateinit var totalDurationTextView: TextView

    private val updateSeekBarRunnable = Runnable {
        updateSeekBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, currentSong)
        lifecycleTextView = findViewById(R.id.lifecycleTextView)
        seekBar = findViewById(R.id.seekBar)
        currentPositionTextView = findViewById(R.id.currentPositionTextView)
        totalDurationTextView = findViewById(R.id.totalDurationTextView)

        seekBar.max = mediaPlayer.duration
        totalDurationTextView.text = formatTime(mediaPlayer.duration)

        mediaPlayer.setOnCompletionListener {
            playbackPosition = 0
            mediaPlayer.seekTo(playbackPosition)
            seekBar.progress = playbackPosition
            currentPositionTextView.text = formatTime(playbackPosition)
            logAndDisplayLifecycleEvent("Playback completed")
        }

        mediaPlayer.setOnPreparedListener {
            totalDurationTextView.text = formatTime(mediaPlayer.duration)
        }

        mediaPlayer.setOnBufferingUpdateListener { _, percent ->
            seekBar.secondaryProgress = percent * mediaPlayer.duration / 100
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                    currentPositionTextView.text = formatTime(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        logAndDisplayLifecycleEvent("onCreate called")
    }

    fun onPlayButtonClick(view: View) {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            updateSeekBar()
        }
        logAndDisplayLifecycleEvent("Play button clicked")
    }

    fun onPauseButtonClick(view: View) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            playbackPosition = mediaPlayer.currentPosition
            seekBar.removeCallbacks(updateSeekBarRunnable)
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
            updateSeekBar()
        } else {
            mediaPlayer.start()
            updateSeekBar()
        }
        logAndDisplayLifecycleEvent("onResume called")
    }

    override fun onPause() {
        super.onPause()
        // Save the current playback position and pause the player
        playbackPosition = mediaPlayer.currentPosition
        mediaPlayer.pause()
        seekBar.removeCallbacks(updateSeekBarRunnable)
        logAndDisplayLifecycleEvent("onPause called")
    }

    override fun onStop() {
        super.onStop()
        // Release the MediaPlayer resources if not needed anymore
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
        seekBar.removeCallbacks(updateSeekBarRunnable)
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

    private fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition
        currentPositionTextView.text = formatTime(mediaPlayer.currentPosition)

        if (mediaPlayer.isPlaying) {
            seekBar.postDelayed(updateSeekBarRunnable, 1000)
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}

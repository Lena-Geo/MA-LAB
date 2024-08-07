package com.example.lab5

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var catSound: MediaPlayer
    private lateinit var dogSound: MediaPlayer
    private lateinit var elephantSound: MediaPlayer
    private lateinit var frogSound: MediaPlayer

    private var currentlyPlaying: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize MediaPlayer with animal sounds
        catSound = MediaPlayer.create(this, R.raw.cat_sound)
        dogSound = MediaPlayer.create(this, R.raw.dog_sound)
        elephantSound = MediaPlayer.create(this, R.raw.elephant_sound)
        frogSound = MediaPlayer.create(this, R.raw.frog_sound)

        // Set click listeners on the images
        findViewById<ImageView>(R.id.cat_image).setOnClickListener {
            playSound(catSound)
        }

        findViewById<ImageView>(R.id.dog_image).setOnClickListener {
            playSound(dogSound)
        }

        findViewById<ImageView>(R.id.elephant_image).setOnClickListener {
            playSound(elephantSound)
        }

        findViewById<ImageView>(R.id.frog_image).setOnClickListener {
            playSound(frogSound)
        }
    }

    private fun playSound(mediaPlayer: MediaPlayer) {
        currentlyPlaying?.let {
            if (it.isPlaying) {
                it.stop()
                it.prepare() // Prepare it for the next use
            }
        }
        currentlyPlaying = mediaPlayer
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        catSound.release()
        dogSound.release()
        elephantSound.release()
        frogSound.release()
    }
}

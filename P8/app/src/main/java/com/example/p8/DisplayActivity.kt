package com.example.p8

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val textView: TextView = findViewById(R.id.textView)
        val gridView: GridView = findViewById(R.id.gridViewDisplay)

        // Retrieve text and image list from SharedPreferences
        val text = sharedPreferences.getString("text", "No text available")
        val imageSet = sharedPreferences.getStringSet("images", emptySet())

        // Decode each Base64 image and store in an array
        val decodedImageList = ArrayList<Bitmap>()
        imageSet?.forEach { encodedImage ->
            val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)
            val decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            decodedImageList.add(decodedBitmap)
        }

        // Set text and populate GridView with images
        textView.text = text
        gridView.adapter = BitmapAdapter(this, decodedImageList)
    }

    // BitmapAdapter for displaying Bitmap images
    inner class BitmapAdapter(private val context: Context, private val bitmaps: ArrayList<Bitmap>) : BaseAdapter() {

        override fun getCount(): Int {
            return bitmaps.size
        }

        override fun getItem(position: Int): Any {
            return bitmaps[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val imageView: ImageView
            if (convertView == null) {
                imageView = ImageView(context)
                imageView.layoutParams = ViewGroup.LayoutParams(300, 300)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                imageView = convertView as ImageView
            }

            imageView.setImageBitmap(bitmaps[position])
            return imageView
        }
    }
}

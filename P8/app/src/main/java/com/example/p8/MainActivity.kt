package com.example.p8

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var gridView: GridView
    private val PICK_IMAGE_REQUEST = 1
    private val imageUriList = ArrayList<Uri>()
    private val encodedImagesList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val editText: EditText = findViewById(R.id.editText)
        gridView = findViewById(R.id.gridView)
        val buttonSelectImages: Button = findViewById(R.id.buttonSelectImages)
        val buttonSubmit: Button = findViewById(R.id.buttonSubmit)

        // Handle Select Images button click
        buttonSelectImages.setOnClickListener {
            openGallery()
        }

        // Handle Submit button click
        buttonSubmit.setOnClickListener {
            val textInput = editText.text.toString()

            if (imageUriList.isNotEmpty()) {
                // Encode selected images to Base64 strings
                for (uri in imageUriList) {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    val encodedImage = encodeImageToBase64(bitmap)
                    encodedImagesList.add(encodedImage)
                }

                // Store text and image list in SharedPreferences
                editor.putString("text", textInput)
                editor.putStringSet("images", encodedImagesList.toSet())
                editor.apply()

                // Start DisplayActivity
                val intent = Intent(this, DisplayActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select at least one image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Open gallery to select multiple images
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGE_REQUEST)
    }

    // Handle the result of the image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (data.clipData != null) {
                    // Handle multiple images
                    val clipData = data.clipData
                    for (i in 0 until clipData!!.itemCount) {
                        val imageUri = clipData.getItemAt(i).uri
                        imageUriList.add(imageUri)
                    }
                } else if (data.data != null) {
                    // Handle single image
                    val imageUri = data.data
                    if (imageUri != null) {
                        imageUriList.add(imageUri)
                    }
                }
                // Display selected images in GridView
                displaySelectedImages()
            }
        }
    }

    // Display the selected images in GridView
    private fun displaySelectedImages() {
        gridView.adapter = ImageAdapter(this, imageUriList)
    }

    // Helper function to encode bitmap to Base64 string
    private fun encodeImageToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // ImageAdapter for displaying Uri images
    inner class ImageAdapter(private val context: Context, private val imageList: ArrayList<Uri>) : BaseAdapter() {

        override fun getCount(): Int {
            return imageList.size
        }

        override fun getItem(position: Int): Any {
            return imageList[position]
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

            imageView.setImageURI(imageList[position])
            return imageView
        }
    }
}

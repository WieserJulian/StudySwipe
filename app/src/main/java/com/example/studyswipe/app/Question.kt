package com.example.studyswipe.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Question(
    val question: String,
    val imagePath: String,
    val points: Number
) {
    fun getImageBitmap(): Bitmap? {
        return BitmapFactory.decodeFile(imagePath)
    }
}

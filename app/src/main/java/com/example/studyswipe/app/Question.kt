package com.example.studyswipe.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Question(
    var question: String,
    var awnser: String,
    val imagePath: String,
    val points: Number,
    var previousAttempt: PreviousAttempt
) {
    fun getImageBitmap(): Bitmap? {
        return BitmapFactory.decodeFile(imagePath)
    }
}

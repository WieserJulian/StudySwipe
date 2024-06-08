package com.example.studyswipe.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Question(
    var question: String,
    var answer: String,
    val points: Number,
    val imagePath: String? = "",
    var previousAttempt: PreviousAttempt? = PreviousAttempt.POSITIVE
) {
    fun getImageBitmap(): Bitmap? {
        return BitmapFactory.decodeFile(imagePath)
    }
}

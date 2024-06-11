package com.example.studyswipe.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Question(
    var question: String,
    var answer: String,
    val points: Number,
    val imagePath: String? = "",
    var previousAttempt: PreviousAttempt? = PreviousAttempt.POSITIVE
) : Parcelable {
    fun getImageBitmap(): Bitmap? {
        return BitmapFactory.decodeFile(imagePath)
    }
}

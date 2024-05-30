package com.example.studyswipe.app

import android.content.Context
import com.example.studyswipe.utils.FileUtils

object TopicLibrary {
    var topics: List<Topic> = listOf()

    fun initialize(context: Context) {
        topics = FileUtils.loadFromJson(context)
    }
}


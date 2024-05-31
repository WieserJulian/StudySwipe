package com.example.studyswipe.utils

import android.content.Context
import com.example.studyswipe.app.Question
import com.example.studyswipe.app.Topic
import com.example.studyswipe.app.TopicLibrary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object FileUtils {
    private const val FILE_NAME = "StudySwipeSave.json"

    fun saveAsJson(context: Context) {
        val gson = Gson()
        val jsonString = gson.toJson(TopicLibrary.topics)

        val file = File(context.filesDir, FILE_NAME)
        file.writeText(jsonString)
    }

    fun loadFromJson(context: Context): List<Topic> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) {
            // Return an empty list or default topics if the file doesn't exist
            return listOf(Topic(
                name = "General Knowledge",
                questions = listOf(Question(
                    question = "What is 5 + 7?",
                    imagePath = "test.jpg",
                    points = 12
                ), Question(
                    question = "What is the capital of France?",
                    imagePath = "YIKES.png",
                    points = 42
                )
                )
            ))
        }

        val jsonString = file.readText()
        val gson = Gson()
        val type = object : TypeToken<List<Topic>>() {}.type

        return gson.fromJson(jsonString, type)
    }

    // TODO: virtual safe of questions for easy access

}


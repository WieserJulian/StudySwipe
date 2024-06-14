package com.example.studyswipe.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.studyswipe.app.PreviousAttempt
import com.example.studyswipe.app.Question
import com.example.studyswipe.app.Topic
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.app.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {
    private const val FILE_NAME = "StudySwipeSave.json"
    private val gson = Gson()

    data class DataWrapper(val user: User, val topics: ArrayList<Topic>)

    fun saveAsJson(context: Context) {
        val dataWrapper = DataWrapper(User, TopicLibrary.topics)
        val jsonString = gson.toJson(dataWrapper)

        val file = File(context.filesDir, FILE_NAME)
        file.writeText(jsonString)
    }

    fun loadFromJson(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) {
            // Return an empty list or default topics if the file doesn't exist
            TopicLibrary.topics = ArrayList(listOf<Topic>(Topic(
                name = "MATH",
                questions = listOf<Question>(
                    Question(
                        question = "What is 5 + 7?",
                        answer = "12",
                        points = 5,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is 8 * 6?",
                        imagePath = "math2.jpg",
                        answer = "48",
                        points = 2,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is the square root of 81?",
                        imagePath = "math3.jpg",
                        answer = "9",
                        points = 1,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is 15 - 7?",
                        imagePath = "math4.jpg",
                        answer = "8",
                        points = 1,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is 9 / 3?",
                        imagePath = "math5.jpg",
                        answer = "3",
                        points = 1,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is the value of pi (π) to 2 decimal places?",
                        imagePath = "math6.jpg",
                        answer = "3.14",
                        points = 2,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is the perimeter of a rectangle with length 5 and width 3?",
                        imagePath = "math7.jpg",
                        answer = "16",
                        points = 3,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is 7 squared?",
                        imagePath = "math8.jpg",
                        answer = "49",
                        points = 4,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is the factorial of 5 (5!)?",
                        imagePath = "math9.jpg",
                        answer = "120",
                        points = 2,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is the sum of the angles in a triangle?",
                        imagePath = "math10.jpg",
                        answer = "180 degrees",
                        points = 1,
                        previousAttempt = PreviousAttempt.POSITIVE
                    )
                )
            ), Topic(
                name = "CARS",
                questions = listOf(
                    Question(
                        question = "What is the top speed of a Bugatti Veyron?",
                        answer = "267 mph",
                        points = 3,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "Which company manufactures the Mustang?",
                        answer = "Ford",
                        points = 1,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is the most common type of fuel used in cars?",
                        answer = "Gasoline",
                        points = 5,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "Which car is known as the 'People's Car'?",
                        answer = "Volkswagen Beetle",
                        points = 3,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What is the symbol for the car company Tesla?",
                        answer = "T",
                        points = 1,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "Which car company produces the 911 model?",
                        answer = "Porsche",
                        points = 2,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "In which year was the first car made?",
                        answer = "1886",
                        points = 2,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What does the acronym BMW stand for?",
                        answer = "Bayerische Motoren Werke",
                        points = 3,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "Which car is often referred to as a 'supercar killer'?",
                        answer = "Nissan GT-R",
                        points = 2,
                        previousAttempt = PreviousAttempt.POSITIVE
                    ),
                    Question(
                        question = "What type of engine is commonly used in hybrid cars?",
                        answer = "Internal combustion engine and electric motor",
                        points = 1,
                        previousAttempt = PreviousAttempt.POSITIVE
                    )
                )
            ),
                Topic(
                    name = "HISTORY",
                    questions = listOf(
                        Question(
                            question = "In which year did World War I begin?",
                            answer = "1914",
                            points = 2,
                            previousAttempt = PreviousAttempt.POSITIVE
                        ),
                        Question(
                            question = "Who was the first president of the United States?",
                            answer = "George Washington",
                            points = 2,
                            previousAttempt = PreviousAttempt.NEGATIVE
                        ),
                        Question(
                            question = "Who was the first female president of the United States?",
                            answer = "Hillary Clinton",
                            points = 3,
                            previousAttempt = PreviousAttempt.POSITIVE
                        )))
            ))
            return
        }

        val jsonString = file.readText()
        val type = object : TypeToken<DataWrapper>() {}.type
        val dataWrapper: DataWrapper = gson.fromJson(jsonString, type)

        User.positiveCounter = dataWrapper.user.positiveCounter
        User.negativeCounter = dataWrapper.user.negativeCounter
        User.retryCounter = dataWrapper.user.retryCounter
        User.points = dataWrapper.user.points
        User.lastTopic = dataWrapper.user.lastTopic

        TopicLibrary.topics = dataWrapper.topics
    }

    fun exportTopics() {
        val data = gson.toJson(TopicLibrary.topics)

        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDirectory, "EXPORT_STUDYSWIPE_TOPICS.expSS")
            try {
                val fos = FileOutputStream(file)
                fos.write(data.toByteArray())
                fos.close()
            } catch (e: IOException) {
                // Handle the exception
                Log.e("TopicLibrary", "Error exporting topics", e)
                e.printStackTrace()
            }
    }

    fun importTopics(file: File, context: Context) {


        val extension = MimeTypeMap.getFileExtensionFromUrl(file.absolutePath)

        if (extension != "expSS") {
            // Show an error message
            println("Invalid file type. Only .expSS files are allowed.")
            return
        }

        try {val jsonString = file.readText()
            val type = object : TypeToken<ArrayList<Topic>>() {}.type
            val importedTopics: ArrayList<Topic> = gson.fromJson(jsonString, type)
            Log.d("FileUtils", "importedTopics: $importedTopics")
            for (topic in importedTopics) {
                TopicLibrary.addTopic(topic.name, topic.questions)
            }
            saveAsJson(context)
        } catch (e: IOException) {
            // Handle the exception
            e.printStackTrace()
        }


    }
}


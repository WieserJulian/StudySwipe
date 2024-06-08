package com.example.studyswipe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question

class HomeViewModel : ViewModel() {

    private val _topics = MutableLiveData<List<String>>(listOf("Math", "Science", "History"))
    val topics: LiveData<List<String>> = _topics

    val topicQuestion: Map<String, List<Question>> = mapOf(
        "Math" to listOf(
            Question("What is 2 + 2?", "4", 0),
            Question("What is 3 * 3?", "9", 0)
        ),
        "Science" to listOf(
            Question("What is the atomic number of Hydrogen?", "1",  0),
            Question("What is the atomic number of Helium?", "2", 0)
        ),
        "History" to listOf(
            Question("What year was the Declaration of Independence signed?", "1776", 0),
            Question("What year did the Civil War end?", "1865",  0)
        )
    )


    fun getQuestion(topic: String): List<Question> {
        return topicQuestion[topic] ?: listOf()
    }

    fun getLastTopic(): String {
        return _topics.value?.last() ?: "No topics"
    }
}
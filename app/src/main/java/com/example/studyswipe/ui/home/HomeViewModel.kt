package com.example.studyswipe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Queue

class HomeViewModel : ViewModel() {

    private val _topics = MutableLiveData<List<String>>(listOf("Math", "Science", "History"))
    val topics: LiveData<List<String>> = _topics

    val topicQuestion: Map<String, List<Pair<String, String>>> = mapOf(
        "Math" to listOf(Pair("What is 1 + 1?", "2"), Pair("What is 2 + 2?", "4")),
        "Science" to listOf(
            Pair("What is the powerhouse of the cell?", "Mitochondria"),
            Pair("What is the atomic number of Hydrogen?", "1")
        ),
        "History" to listOf(
            Pair("What year did the Titanic sink?", "1912"),
            Pair("Who was the first president of the United States?", "George Washington")
        )
    )


    fun getQuestion(topic: String): List<Pair<String, String>> {
        return topicQuestion[topic] ?: listOf()
    }

    fun getLastTopic(): String {
        return _topics.value?.last() ?: "No topics"
    }
}
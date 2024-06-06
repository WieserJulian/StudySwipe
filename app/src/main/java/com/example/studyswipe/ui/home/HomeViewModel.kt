package com.example.studyswipe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Queue

class HomeViewModel : ViewModel() {

    private val _topics = MutableLiveData<List<String>>(listOf("Math", "Science", "History"))
    val topics: LiveData<List<String>> = _topics

    fun getLastTopic(): String {
        return _topics.value?.last() ?: "No topics"
    }
}
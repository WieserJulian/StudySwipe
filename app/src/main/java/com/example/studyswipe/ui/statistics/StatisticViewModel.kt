package com.example.studyswipe.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is statistic Fragment"
    }
    val text: LiveData<String> = _text
}
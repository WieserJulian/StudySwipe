package com.example.studyswipe.ui.swipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SwipeCardViewModel : ViewModel() {
    // TODO: Replace String with Question object

    private val _positive = MutableLiveData<MutableList<Pair<String, String>>>(mutableListOf())
    val positive: LiveData<MutableList<Pair<String, String>>> = _positive
    private val _negative = MutableLiveData<MutableList<Pair<String, String>>>(mutableListOf())
    val negative: LiveData<MutableList<Pair<String, String>>> = _negative
    private val _retry = MutableLiveData<MutableList<Pair<String, String>>>(mutableListOf())
    val retry: LiveData<MutableList<Pair<String, String>>> = _retry

    private val _allQuestion = MutableLiveData<MutableList<Pair<String, String>>>(mutableListOf())
    val allQuestion: LiveData<MutableList<Pair<String, String>>> = _allQuestion
    fun getNextQuestion(): Pair<String, String> {
        if (hasNewQuestion()) {
            return allQuestion.value?.removeAt(0) ?: Pair("", "")
        }
        return Pair("", "")
    }

    fun hasNewQuestion(): Boolean {
        return allQuestion.value?.isNotEmpty() ?: false
    }

    fun getAllQuestions(): Map<String, MutableList<Pair<String, String>>?> {
        return mapOf(
            "positive" to positive.value,
            "negative" to negative.value,
            "retry" to retry.value
        )
    }

    fun setAllQuestion(question: List<Pair<String, String>>) {
        _allQuestion.value = question.toMutableList()
    }

    fun addToPositive(activeQuestion: Pair<String, String>) {
        _positive.value?.add(activeQuestion)
        _allQuestion.value?.remove(activeQuestion)
    }

    fun addToNegative(activeQuestion: Pair<String, String>) {
        _negative.value?.add(activeQuestion)
        _allQuestion.value?.remove(activeQuestion)
    }


}
package com.example.studyswipe.ui.swipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question

class SwipeCardViewModel : ViewModel() {
    // TODO: Replace String with Question object

    private val _positive = MutableLiveData<MutableList<Question>>(mutableListOf())
    val positive: LiveData<MutableList<Question>> = _positive
    private val _negative = MutableLiveData<MutableList<Question>>(mutableListOf())
    val negative: LiveData<MutableList<Question>> = _negative
    private val _retry = MutableLiveData<MutableList<Question>>(mutableListOf())
    val retry: LiveData<MutableList<Question>> = _retry

    private val _allQuestion = MutableLiveData<MutableList<Question>>(mutableListOf())
    val allQuestion: LiveData<MutableList<Question>> = _allQuestion
    fun getNextQuestion(): Question {
        if (hasNewQuestion()) {
            return allQuestion.value?.removeAt(0) ?: Question("", "",  0)
        }
        return Question("", "",  0)
    }

    fun hasNewQuestion(): Boolean {
        return allQuestion.value?.isNotEmpty() ?: false
    }

    fun getAllQuestions(): Map<String, MutableList<Question>?> {
        return mapOf(
            "positive" to positive.value,
            "negative" to negative.value,
            "retry" to retry.value
        )
    }

    fun setAllQuestion(question: List<Question>) {
        _allQuestion.value = question.toMutableList()
    }

    fun addToPositive(activeQuestion: Question) {
        _positive.value?.add(activeQuestion)
        _allQuestion.value?.remove(activeQuestion)
    }

    fun addToNegative(activeQuestion: Question) {
        _negative.value?.add(activeQuestion)
        _allQuestion.value?.remove(activeQuestion)
    }


}
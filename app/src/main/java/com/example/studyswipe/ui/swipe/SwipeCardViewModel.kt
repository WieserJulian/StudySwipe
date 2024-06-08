package com.example.studyswipe.ui.swipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question

class SwipeCardViewModel : ViewModel() {
    // TODO: Replace String with Question object

    private val _allQuestion = MutableLiveData<MutableList<Question>>(mutableListOf())
    val allQuestion: LiveData<MutableList<Question>> = _allQuestion

    private val _doneQuestion = MutableLiveData<MutableList<Question>>(mutableListOf())
    val doneQuestion: LiveData<MutableList<Question>> = _doneQuestion
    fun getNextQuestion(): Question {
        if (hasNewQuestion()) {
            return allQuestion.value?.removeAt(0) ?: Question("", "",  0)
        }
        return Question("", "",  0)
    }

    fun hasNewQuestion(): Boolean {
        return allQuestion.value?.isNotEmpty() ?: false
    }

    fun setAllQuestion(question: List<Question>) {
        _allQuestion.value = question.toMutableList()
    }

    fun addQuestion(activeQuestion: Question) {
        _doneQuestion.value?.add(activeQuestion)
        _allQuestion.value?.remove(activeQuestion)
    }

    fun getDoneQuestion(): List<Question> {
        return doneQuestion.value ?: mutableListOf()
    }


}
package com.example.studyswipe.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question

class EditQuestionViewModel : ViewModel() {

    private val mutableSelectedItem = MutableLiveData<MutableMap<Int, Question>>(mutableMapOf())
    val question: LiveData<MutableMap<Int, Question>> get() = mutableSelectedItem

    fun addQuestion(id: Int, q: Question) {
        mutableSelectedItem.value?.set(id, q);
    }

    fun getQuestion(id: Int): Question? {
        return mutableSelectedItem.value?.get(id)
    }

    fun updateQuestion(id: Int, q: Question) {
        mutableSelectedItem.value?.set(id, q)
    }

    fun removeQuestion(id: Int) {
        mutableSelectedItem.value?.remove(id)
    }

    fun getAllQuestions(): List<Question> {
        return mutableSelectedItem.value?.values?.toList() ?: listOf()
    }
}
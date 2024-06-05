package com.example.studyswipe.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question

class EditQuestionViewModel : ViewModel() {

    private val mutableSelectedItem = MutableLiveData<MutableMap<String, Question>>(mutableMapOf())
    val question: LiveData<MutableMap<String, Question>> get() = mutableSelectedItem

    fun addQuestion(id: String, q: Question) {
        mutableSelectedItem.value?.set(id, q);
    }

    fun getQuestion(id: String): Question? {
        return mutableSelectedItem.value?.get(id)
    }

    fun removeQuestion(id: String) {
        mutableSelectedItem.value?.remove(id)
    }

    fun getAllQuestions(): List<Question> {
        return mutableSelectedItem.value?.values?.toList() ?: listOf()
    }
}
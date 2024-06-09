package com.example.studyswipe.ui.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question

class EditQuestionViewModel : ViewModel() {

    private val mutableSelectedItem = MutableLiveData<MutableMap<String, Question>>(mutableMapOf())
    val question: LiveData<MutableMap<String, Question>> get() = mutableSelectedItem

    private val _isInPreviewMode = MutableLiveData<Boolean>()
    val isInPreviewMode: LiveData<Boolean> get() = _isInPreviewMode

    fun setPreviewMode(isPreview: Boolean) {
        _isInPreviewMode.value = isPreview
    }

    fun addQuestion(id: String, q: Question) {
        mutableSelectedItem.value?.set(id, q);
    }

    fun getQuestion(id: String): Question? {
        return mutableSelectedItem.value?.get(id)
    }

    fun updateQuestion(id: String, q: Question) {
        mutableSelectedItem.value?.set(id, q)
    }

    fun removeQuestion(id: String) {
        mutableSelectedItem.value?.remove(id)
    }

    fun getAllQuestions(): MutableList<Question> {
        return mutableSelectedItem.value?.values?.toMutableList() ?: mutableListOf()
    }
}
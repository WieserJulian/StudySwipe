package com.example.studyswipe.ui.addedit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question
import java.util.UUID

class EditQuestionViewModel : ViewModel() {

    private var mutableSelectedItem = MutableLiveData<MutableMap<String, Question>>(mutableMapOf())
    val question: LiveData<MutableMap<String, Question>> get() = mutableSelectedItem

    private val _isInPreviewMode = MutableLiveData<Boolean>()
    val isInPreviewMode: LiveData<Boolean> get() = _isInPreviewMode

    fun setPreviewMode(isPreview: Boolean) {
        _isInPreviewMode.value = isPreview
    }

    fun addQuestion(id: String, q: Question) {
        Log.d("EditQuestionViewModel", "addQuestion: $id, $q")
        mutableSelectedItem.value?.set(id, q)
    }


    fun addQuestions(questions: List<Question>) {
        questions.forEach() {
            mutableSelectedItem.value?.set(UUID.randomUUID().toString(), it)
        }
    }

    fun getQuestion(id: String): Question? {
        Log.d("EditQuestionViewModel", "getQuestion: $id")
        return mutableSelectedItem.value?.get(id)
    }

    fun updateQuestion(id: String, q: Question) {
        Log.d("EditQuestionViewModel", "updateQuestion: $id, $q")
        mutableSelectedItem.value?.set(id, q)
    }

    fun removeQuestion(id: String) {
        Log.d("EditQuestionViewModel", "removeQuestion: $id")
        mutableSelectedItem.value?.remove(id)
    }

    fun getAllQuestionsUuid(): List<Pair<String, Question>> {
        return mutableSelectedItem.value?.toList() ?: listOf()
    }

    fun getAllQuestions(): List<Question> {
        return mutableSelectedItem.value?.values?.toList() ?: listOf()
    }

    fun resetQuestions() {
        mutableSelectedItem.value = mutableMapOf()
    }
}
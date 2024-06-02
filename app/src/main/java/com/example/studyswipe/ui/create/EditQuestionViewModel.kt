package com.example.studyswipe.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyswipe.app.Question

class EditQuestionViewModel : ViewModel() {

    private val mutableSelectedItem = MutableLiveData<Question>()
    val question: LiveData<Question> get() = mutableSelectedItem

    fun question(q: Question) {
        mutableSelectedItem.value = q
    }
}
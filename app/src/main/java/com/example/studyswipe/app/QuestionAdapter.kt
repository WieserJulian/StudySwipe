package com.example.studyswipe.app

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyswipe.ui.create.EditQuestionFragment

class QuestionAdapter(private val fragmentManager: FragmentManager, private val questions: MutableList<EditQuestionFragment>) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(val fragment: EditQuestionFragment) : RecyclerView.ViewHolder(fragment.requireView())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val addQuestionFragment = EditQuestionFragment()
        fragmentManager.beginTransaction().add(addQuestionFragment, "question${viewType}").commitNow()
        return QuestionViewHolder(addQuestionFragment)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        // You can set up your fragment here
    }

    override fun getItemCount() = questions.size
}
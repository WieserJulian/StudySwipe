package com.example.studyswipe.ui.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.studyswipe.app.Question
import com.example.studyswipe.databinding.FragmentEditQuestionBinding

class EditQuestionFragment : Fragment() {
    private var _binding: FragmentEditQuestionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("EditQuestionFragment onCreateView")
        _binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getQuestion(): Question {
        val questionText = binding.editTextTextMultiLine.text.toString()
        // Assuming imagePath and points are default values for now
        return Question(questionText, "", 0)
    }


}
package com.example.studyswipe.ui.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studyswipe.app.Question
import com.example.studyswipe.databinding.FragmentEditQuestionBinding
import java.util.UUID

class EditQuestionFragment : Fragment() {
    private var _binding: FragmentEditQuestionBinding? = null
    private var param: Int = UUID.randomUUID().hashCode()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val question = Question("","", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("EditQuestionFragment onCreate: $param")
        println(arguments?.getInt("questionIDChange Theme"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("EditQuestionFragment onCreateView")

        var editViewModel = ViewModelProvider(requireActivity()).get(EditQuestionViewModel::class.java)
        _binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        editViewModel.addQuestion(param, question)

        binding.editTextTextMultiLine.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                question.question = s.toString()
                editViewModel.updateQuestion(param, question)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no-op
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // no-op
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
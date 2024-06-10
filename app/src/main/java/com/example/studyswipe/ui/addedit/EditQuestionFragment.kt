package com.example.studyswipe.ui.addedit

import EditQuestionDialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studyswipe.app.Question
import com.example.studyswipe.databinding.FragmentEditQuestionBinding
import com.example.studyswipe.ui.card.CardFragment
import java.util.UUID

class EditQuestionFragment : Fragment(), CardFragment.OnCardListener {
    private var _binding: FragmentEditQuestionBinding? = null
    private var param: Int = UUID.randomUUID().hashCode()
    private val binding get() = _binding!!

    private lateinit var question: Question
    private var showingFront = true
    private lateinit var editViewModel: EditQuestionViewModel
    private lateinit var questionID: String


    override fun onCardClick() {
        println("EditQuestionFragment onCardClick")
        if (editViewModel.isInPreviewMode.value == false) {
            val dialog = EditQuestionDialogFragment()
            dialog.arguments = Bundle().apply {
                putString("questionUID", questionID)
            }
            dialog.show(parentFragmentManager, "EditQuestionDialogFragment")

            return
        }
        binding.editQuestionCardFragment.getFragment<CardFragment>().flipCard(if (showingFront) question.question else question.answer)
        showingFront = !showingFront
    }

    override fun getQuestion(): Question {
        return question
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("EditQuestionFragment onCreateView")

        editViewModel = ViewModelProvider(requireActivity()).get(EditQuestionViewModel::class.java)
        _binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val uuid = arguments?.getString("questionUID")
        if (uuid == null) {
            root.visibility = View.GONE
            return root
        }
        questionID = uuid
        question = editViewModel.getQuestion(questionID)!!

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
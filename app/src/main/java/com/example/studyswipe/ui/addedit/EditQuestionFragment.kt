package com.example.studyswipe.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studyswipe.app.Question
import com.example.studyswipe.databinding.FragmentEditQuestionBinding
import com.example.studyswipe.ui.card.CardFragment
import java.util.UUID

class EditQuestionFragment : Fragment(), CardFragment.OnCardListener {
    private var _binding: FragmentEditQuestionBinding? = null
    private var param: Int = UUID.randomUUID().hashCode()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var question: Question
    private var showingFront = true
    private lateinit var editViewModel: EditQuestionViewModel


    override fun onCardClick() {
        println("EditQuestionFragment onCardClick")
        if (editViewModel.isInPreviewMode.value == false) {
            if (!showingFront) {
                binding.editQuestionCardFragment.getFragment<CardFragment>().flipCard(question.question)
                showingFront = true
            }
            // TODO Edit the question
            AlertDialog.Builder(requireContext())
                .setTitle("Edit Question")
                .setMessage("WIP")
                .show()
            return
        }
        binding.editQuestionCardFragment.getFragment<CardFragment>().flipCard(if (showingFront) question.question else question.answer)
        showingFront = !showingFront
    }

    override fun getQuestion(): Question {
        return question
    }

    override fun shouldFlipCorner(): Boolean {
        return false
    }

    override fun endSwipe(cView: CardView, cardStart: Float) {
    }

    override fun preventSwipe() {
    }

    override fun swipeHandling(x: Float, cardStart: Float) {
    }

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

        editViewModel = ViewModelProvider(requireActivity()).get(EditQuestionViewModel::class.java)
        _binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val questionID = arguments?.getString("questionUID")
        if (questionID == null) {
            root.visibility = View.GONE
            return root
        }
        question = editViewModel.getQuestion(questionID)!!

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
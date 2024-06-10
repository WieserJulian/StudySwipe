package com.example.studyswipe.ui.swipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.app.PreviousAttempt
import com.example.studyswipe.app.Question
import com.example.studyswipe.app.Topic
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.app.User
import com.example.studyswipe.databinding.FragmentSwipeCardBinding
import com.example.studyswipe.ui.card.CardFragment
import com.example.studyswipe.utils.Constants

class SwipeCardFragment : Fragment(), CardFragment.OnCardListener {

    private var hasMoved: Boolean = false
    private var hasFlipped: Boolean = false
    private lateinit var swipeCardViewModel: SwipeCardViewModel
    private lateinit var activeTopic: Topic
    private lateinit var activeQuestion: Question
    private var state: PreviousAttempt? = null
    private var topicName: String = ""

    private var _binding: FragmentSwipeCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var swipeCardFragment: CardFragment


    override fun onCardClick() {
        if (!hasFlipped) {
            hasFlipped = true
            swipeCardFragment.flipCard(activeQuestion.answer)
        }
    }

    override fun getQuestion(): Question {
        return activeQuestion
    }

    override fun shouldFlipCorner(): Boolean {
        return true
    }

    override fun preventSwipe() {

    }

    override fun swipeHandling(x: Float, cardStart: Float) {
        if (x < -Constants.MIN_SWIPE_DISTANCE && !hasMoved && state != PreviousAttempt.POSITIVE) {
            Log.d("SwipeCardFragment", "Swiped left")
            state = PreviousAttempt.POSITIVE

        } else if (x - cardStart > Constants.MIN_SWIPE_DISTANCE && state != PreviousAttempt.NEGATIVE) {
            Log.d("SwipeCardFragment", "Swiped right")
            state = PreviousAttempt.NEGATIVE
        }
    }

    override fun endSwipe(cView: CardView, cardStart: Float) {
        hasMoved = false
        if (!swipeCardViewModel.hasNewQuestion() && state != null) {
            handleFinishedQuestions(cView)
        } else {
            cView.animate()
                .x(cardStart)
                .setDuration(20)
                .start()

            if (state != null) {
                safeAnswer(state!!)
                loadNextQuestion()
            }
        }
    }

    override fun enableSwipe(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeCardBinding.inflate(inflater, container, false)

        swipeCardViewModel =
            ViewModelProvider(this)[SwipeCardViewModel::class.java]

        val root: View = binding.root
        topicName = arguments?.getString("topic") ?: ""
        val questionType = PreviousAttempt.valueOf(
            arguments?.getString("questionType") ?: PreviousAttempt.POSITIVE.toString()
        )
        activeTopic = TopicLibrary.getTopic(topicName)
        var questions = activeTopic.questions
        when (questionType) {
            PreviousAttempt.RETRY -> questions =
                questions.filter { it.previousAttempt == PreviousAttempt.NEGATIVE || it.previousAttempt == PreviousAttempt.RETRY }

            PreviousAttempt.NEGATIVE -> questions =
                questions.filter { it.previousAttempt == PreviousAttempt.NEGATIVE }

            PreviousAttempt.POSITIVE -> {}
        }
        swipeCardViewModel.setAllQuestion(questions)
        binding.topicName.text = topicName
        activeQuestion = swipeCardViewModel.getNextQuestion()


        swipeCardFragment = CardFragment()
        childFragmentManager.beginTransaction()
            .add(binding.questionOutlet.id, swipeCardFragment)
            .commit()
        return root
    }


    private fun handleFinishedQuestions(cView: CardView) {
        cView.animate().alpha(0f).start()
        safeAnswer(state!!)
        Log.d("SwipeCardFragment", "No more questions")
        val allQuestion = swipeCardViewModel.getDoneQuestion()
        Log.d(
            "SwipeCardFragment",
            "Positive: ${allQuestion.filter({ it.previousAttempt == PreviousAttempt.POSITIVE })}"
        )
        Log.d(
            "SwipeCardFragment",
            "Negative: ${allQuestion.filter { it.previousAttempt == PreviousAttempt.NEGATIVE }}"
        )
        Log.d(
            "SwipeCardFragment",
            "Retry: ${allQuestion.filter { it.previousAttempt == PreviousAttempt.RETRY }}"
        )
        User.applyQuestionResults(topicName, allQuestion)
        TopicLibrary.updateQuestions(topicName, allQuestion)
        findNavController().navigate(R.id.navigation_home)
    }


    private fun safeAnswer(state: PreviousAttempt) {
        when (state) {
            PreviousAttempt.POSITIVE -> {
                activeQuestion.previousAttempt = PreviousAttempt.POSITIVE
            }

            PreviousAttempt.NEGATIVE -> {
                activeQuestion.previousAttempt = PreviousAttempt.NEGATIVE
            }

            PreviousAttempt.RETRY -> {
                activeQuestion.previousAttempt = PreviousAttempt.RETRY
            }
        }
        swipeCardViewModel.addQuestion(activeQuestion)
    }

    private fun loadNextQuestion() {

        if (!swipeCardViewModel.hasNewQuestion()) {
            return
        }
        state = null
        activeQuestion = swipeCardViewModel.getNextQuestion()
        swipeCardFragment.resetCard()
        swipeCardFragment.loadNextQuestion()
        swipeCardFragment.requireView().animate().setDuration(100).withEndAction {
            hasFlipped = false
        }.start()
    }


}
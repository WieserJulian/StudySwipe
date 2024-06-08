package com.example.studyswipe.ui.swipe

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
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
import com.example.studyswipe.utils.Constants

class SwipeCardFragment : Fragment() {
    private lateinit var cardView: CardView
    private var hasMoved: Boolean = false
    private var hasFlipped: Boolean = false
    private lateinit var swipeCardViewModel: SwipeCardViewModel;
    private lateinit var activeTopic: Topic;
    private lateinit var activeQuestion: Question;
    private var state: PreviousAttempt? = null
    private var topicName: String = ""

    private var _binding: FragmentSwipeCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        Log.d("SwipeCardFragment", "Topic name: $topicName Question type: $questionType")
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
        binding.swapImage.animate().alpha(1f).start()
        binding.questionPoints.animate().alpha(0f).start()
        activeQuestion = swipeCardViewModel.getNextQuestion()
        binding.questionText.text = activeQuestion.question
        binding.questionPoints.text = activeQuestion.points.toString()
        return root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = view.findViewById(R.id.card_view)

        cardView.setOnClickListener {
            if (!hasFlipped) {
                hasFlipped = true
                flipCard(activeQuestion.answer)
            }
        }

        cardView.setOnTouchListener(
            View.OnTouchListener { v, event ->
                if (!hasFlipped) {
                    return@OnTouchListener false
                }
                // variables to store current configuration of quote card.
                val displayMetrics = resources.displayMetrics
                val cardWidth = cardView.width
                val cardStart = (displayMetrics.widthPixels.toFloat() / 2) - (cardWidth / 2)

                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        // get the new co-ordinate of X-axis
                        val newX = event.rawX - (cardWidth / 2)
                        cardView.animate()
                            .x(newX)
                            .setDuration(0)
                            .start()
                        if (cardView.x < -Constants.MIN_SWIPE_DISTANCE && !hasMoved && state != PreviousAttempt.POSITIVE) {
                            Log.d("SwipeCardFragment", "Swiped left")
                            state = PreviousAttempt.POSITIVE

                        } else if (cardView.x - cardStart > Constants.MIN_SWIPE_DISTANCE && state != PreviousAttempt.NEGATIVE) {
                            Log.d("SwipeCardFragment", "Swiped right")
                            state = PreviousAttempt.NEGATIVE
                        }

                    }

                    MotionEvent.ACTION_UP -> {
                        hasMoved = false
                        if (!swipeCardViewModel.hasNewQuestion() && state != null) {
                           handleFinishedQuestions()
                        } else {
                            cardView.animate()
                                .x(cardStart)
                                .setDuration(20)
                                .start()
                            if (state != null) {
                                safeAnswer(state!!)
                                loadNextQuestion()
                            }
                        }
                    }

                }
                v.performClick()
                return@OnTouchListener true
            })
    }

    private fun handleFinishedQuestions() {
        cardView.animate().alpha(0f).start()
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
        User.applyQuestionResults(allQuestion)
        TopicLibrary.updateQuestions(topicName, allQuestion)
        findNavController().navigate(R.id.navigation_home)
    }

    private fun flipCorner() {
        val alS = if (binding.swapImage.alpha == 0f) 1f else 0f
        val alQ = if (binding.questionPoints.alpha == 0f) 1f else 0f
        binding.swapImage.alpha = alS
        binding.questionPoints.alpha = alQ
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
        binding.swapImage.alpha = 1f
        binding.questionPoints.alpha = 0f
        binding.questionText.text = activeQuestion.question
        binding.questionPoints.text = activeQuestion.points.toString()
        cardView.alpha = 0f // Set initial alpha to 0
        cardView.animate().alpha(1f).setDuration(100).withEndAction {
            hasFlipped = false
        }.start()
    }


    private fun flipCard(text: String) {
        val oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f)
        val oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f)

        oa1.interpolator = AccelerateDecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()

        oa1.duration = 125
        oa2.duration = 125

        oa1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                flipCorner()
                binding.questionText.text = text
                oa2.start()
            }
        })
        oa1.start()
    }
}
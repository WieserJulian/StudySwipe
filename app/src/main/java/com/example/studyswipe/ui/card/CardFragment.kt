package com.example.studyswipe.ui.card

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.studyswipe.app.Question
import com.example.studyswipe.databinding.FragmentCardBinding

class CardFragment : Fragment() {

    private lateinit var cardView: CardView
    private var _binding: FragmentCardBinding? = null

    private val binding get() = _binding!!


    private var onCardListener: OnCardListener? = null

    interface OnCardListener {
        fun onCardClick()
        fun getQuestion(): Question
        fun shouldFlipCorner(): Boolean {
            return false
        }
        fun endSwipe(cView: CardView, cardStart: Float) {}
        fun preventSwipe(): Boolean {return true}
        fun swipeHandling(x: Float, cardStart: Float) {}

        fun enableSwipe(): Boolean {
            return false
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.questionText.text = onCardListener?.getQuestion()?.question
        binding.swapImage.alpha = 1f
        binding.questionPoints.alpha = 0f
        cardView = binding.cardView
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView.setOnClickListener {
            onCardListener?.onCardClick()
        }
        if (onCardListener?.enableSwipe() == true) {
            handleSwipe()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCardListener = when {
            parentFragment is OnCardListener -> parentFragment as OnCardListener
            context is OnCardListener -> context
            else -> context as OnCardListener
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleSwipe() {
        cardView.setOnTouchListener(
            View.OnTouchListener { v, event ->
                if (onCardListener?.preventSwipe() == true) {
                    return@OnTouchListener false
                }
                // variables to store current configuration of quote card.
                val displayMetrics = resources.displayMetrics
                val cardWidth = cardView.width
                val cardStart = (displayMetrics.widthPixels - cardWidth) / 2f

                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        // get the new co-ordinate of X-axis
                        val newX = event.rawX - (cardWidth / 2)
                        cardView.animate()
                            .x(newX)
                            .setDuration(0)
                            .start()
                        onCardListener?.swipeHandling(cardView.x, cardStart)


                    }

                    MotionEvent.ACTION_UP -> {
                        onCardListener?.endSwipe(cardView, cardStart)


                    }

                }
                v.performClick()
                return@OnTouchListener true
            })
    }

    fun flipCard(text: String) {
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


    fun resetCard() {
        binding.swapImage.alpha = 1f
        binding.questionPoints.alpha = 0f
        binding.questionText.text = onCardListener!!.getQuestion().question
        binding.questionPoints.text = onCardListener!!.getQuestion().points.toString()
    }

    fun loadNextQuestion() {
        cardView.alpha = 0f // Set initial alpha to 0
        cardView.animate().alpha(1f).setDuration(100).start()
    }

    private fun flipCorner() {
        if (onCardListener?.shouldFlipCorner() == false) {
            return
        }
        val alS = if (binding.swapImage.alpha == 0f) 1f else 0f
        val alQ = if (binding.questionPoints.alpha == 0f) 1f else 0f
        binding.swapImage.alpha = alS
        binding.questionPoints.alpha = alQ
    }

}
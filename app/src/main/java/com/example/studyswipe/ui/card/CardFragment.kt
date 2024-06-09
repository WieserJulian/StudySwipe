package com.example.studyswipe.ui.card

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.studyswipe.R
import com.example.studyswipe.databinding.FragmentCardBinding

class CardFragment : Fragment() {

    private lateinit var cardView: CardView
    private var _binding: FragmentCardBinding? = null

    private val binding get() = _binding!!


    private var onCardListener: CardFragment.OnCardListener? = null
    private var showingFront = true

    interface OnCardListener {
        fun onCardClick()
        fun getQuestionText(): String
        fun getAnswerText(): String
        fun shouldFlipCorner(): Boolean
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.questionText.text = onCardListener?.getQuestionText()
        binding.swapImage.alpha = 1f
        binding.questionPoints.alpha = 0f
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = view.findViewById(R.id.card_view)

        cardView.setOnClickListener {
            onCardListener?.onCardClick()
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
package com.example.studyswipe.ui.swipe

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.studyswipe.R
import com.example.studyswipe.databinding.FragmentHomeBinding
import com.example.studyswipe.databinding.FragmentSwipeCardBinding
import com.example.studyswipe.ui.home.HomeViewModel
import com.example.studyswipe.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

class SwipeCardFragment : Fragment() {
    private lateinit var cardView: CardView
    private var hasMoved: Boolean = false

    private var _binding: FragmentSwipeCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSwipeCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.topicName.text = arguments?.getString("topicName")

        return root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = view.findViewById(R.id.card_view)

        cardView.setOnTouchListener(
            View.OnTouchListener { v, event ->

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
                        if (cardView.x < -Constants.MIN_SWIPE_DISTANCE && !hasMoved) {
                            Log.d("SwipeCardFragment", "Swiped left")
                            hasMoved = true
                        } else if (cardView.x  - cardStart > Constants.MIN_SWIPE_DISTANCE && !hasMoved) {
                            Log.d("SwipeCardFragment", "Swiped right")
                            hasMoved = true
                        }

                    }

                    MotionEvent.ACTION_UP ->  {
                        hasMoved = false
                        cardView.animate().x(cardStart).start()
                    }

                }
                v.performClick()
                return@OnTouchListener true
            })
    }

    private fun onSwipeRight() {
        // Handle right swipe
        cardView.animate().translationX(cardView.width.toFloat()).setDuration(200).start()
    }

    private fun onSwipeLeft() {
        // Handle left swipe
        cardView.animate().translationX(-cardView.width.toFloat()).setDuration(200).start()
    }
}
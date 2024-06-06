package com.example.studyswipe.ui.swipe

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.studyswipe.R
import com.example.studyswipe.databinding.FragmentHomeBinding
import com.example.studyswipe.databinding.FragmentSwipeCardBinding
import com.example.studyswipe.ui.home.HomeViewModel

class SwipeCardFragment : Fragment() {
    private lateinit var cardView: CardView
    private lateinit var gestureDetector: GestureDetectorCompat

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = view.findViewById(R.id.card_view)

        gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val diffX = e2.x - (e1?.x!! ?: 0f)
                if (Math.abs(diffX) > Math.abs(e2.y - e1.y)) {
                    if (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        return true
                    }
                }
                return false
            }
        })

        cardView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
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
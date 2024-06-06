package com.example.studyswipe.ui.swipe

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.studyswipe.R

class SwipeCardFragment : Fragment() {
    private lateinit var cardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_swipe_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = view.findViewById(R.id.card_view)

        cardView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Float = 0.0f
            private var initialY: Float = 0.0f

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = v?.x ?: 0.0f
                        initialY = v?.y ?: 0.0f
                    }
                    MotionEvent.ACTION_MOVE -> {
                        v?.animate()
                            ?.x(event.rawX - initialX)
                            ?.y(event.rawY - initialY)
                            ?.setDuration(0)
                            ?.start()
                    }
                }
                return true
            }
        })
    }
}
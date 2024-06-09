package com.example.studyswipe.ui.topic

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.studyswipe.databinding.FragmentTopicCardBinding

class TopicCardFragment : Fragment() {

    interface OnTopicCardClickListener {
        fun onTopicCardClick(topicName: String)
    }

    private var onTopicCardClickListener: OnTopicCardClickListener? = null

    private var _binding: FragmentTopicCardBinding? = null
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onTopicCardClickListener = when {
            parentFragment is OnTopicCardClickListener -> parentFragment as OnTopicCardClickListener
            context is OnTopicCardClickListener -> context
            else -> context as OnTopicCardClickListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTopicCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setUpCard()
        return root
    }

    fun setUpCard() {
        val topicName = arguments?.getString("topicName") ?: ""
        Log.d("TopicCardFragment", "topicName: $topicName")
        if (topicName == "") {
            binding.root.visibility = View.GONE
        } else {
            binding.root.visibility = View.VISIBLE
        }

        binding.topicName.text = topicName
        binding.topicCard.setOnClickListener {
            onTopicCardClickListener?.onTopicCardClick(topicName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
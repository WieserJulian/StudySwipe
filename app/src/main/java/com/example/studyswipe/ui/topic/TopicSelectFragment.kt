package com.example.studyswipe.ui.topic

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyswipe.app.TopicAdapter
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.databinding.FragmentTopicSelectBinding

class TopicSelectFragment : Fragment(), TopicCardFragment.OnTopicCardClickListener {

    interface OnTopicSelectClickListener {
        fun onTopicSelectClick(topicName: String)
    }


    private var onTopicSelectClickListener: OnTopicSelectClickListener? = null
    private var _binding: FragmentTopicSelectBinding? = null
    private val binding get() = _binding!!
    private val topics = mutableListOf<TopicCardFragment>()
    private lateinit var topicsAdapter: TopicAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onTopicSelectClickListener = when {
            parentFragment is OnTopicSelectClickListener -> parentFragment as OnTopicSelectClickListener
            context is OnTopicSelectClickListener -> context
            else -> throw RuntimeException("$context must implement OnTopicCardClickListener")
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTopicSelectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.topicCardOutlet
        recyclerView.layoutManager = LinearLayoutManager(context)
        topicsAdapter = TopicAdapter(childFragmentManager, topics)
        recyclerView.adapter = topicsAdapter

        TopicLibrary.getTopicsNames().forEach { it ->
            Log.d("TopicSelectFragment", "Adding topic: $it")
            val topic = TopicCardFragment()
            val bundle = Bundle()
            bundle.putString("topicName", it)
            topic.arguments = bundle
            topics.add(topic)
            topicsAdapter.notifyItemInserted(topics.size - 1)
        }

        return root
    }

    override fun onTopicCardClick(topicName: String) {
        onTopicSelectClickListener?.onTopicSelectClick(topicName)
    }
}
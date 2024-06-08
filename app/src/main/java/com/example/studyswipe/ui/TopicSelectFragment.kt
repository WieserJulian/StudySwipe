package com.example.studyswipe.ui

import android.adservices.topics.Topic
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studyswipe.R
import com.example.studyswipe.databinding.FragmentHomeBinding
import com.example.studyswipe.databinding.FragmentQuestionSelectBinding
import com.example.studyswipe.databinding.FragmentTopicSelectBinding

class TopicSelectFragment : Fragment() {

    private var _binding: FragmentTopicSelectBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicSelectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}
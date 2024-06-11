package com.example.studyswipe.ui.home

import QuestionSelectDialogFragment
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.app.PreviousAttempt
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.app.User
import com.example.studyswipe.databinding.FragmentHomeBinding
import com.example.studyswipe.ui.topic.TopicCardFragment
import com.example.studyswipe.utils.Constants

class HomeFragment : Fragment(), TopicCardFragment.OnTopicCardClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onTopicCardClick(topicName: String) {
        openQuestionsHandler(topicName)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.d("HomeFragment", "Users Last Topic: ${User.lastTopic}")
        if (User.lastTopic.isEmpty()) {
            binding.btnLastTopic.visibility = View.GONE
        } else {
            val lastTopic = getString(R.string.lastTopic, User.lastTopic)
            binding.btnLastTopic.text = lastTopic
        }

            val searchView = binding.searchTopics
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                TopicLibrary.getDisplayTopics(Constants.DISPLAY_TOPICS)
            )
            searchView.setAdapter(adapter)
            searchView.setOnItemClickListener { parent, view, position, id ->
                val selectedTopic = parent.getItemAtPosition(position).toString()
                openQuestionsHandler(selectedTopic)
                searchView.dismissDropDown()
            }
        searchView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                var display = TopicLibrary.getTopicsNames().filter{
                    it.contains(searchView.text.toString(), ignoreCase = true)
                }
                if (display.isEmpty()) {
                    display = TopicLibrary.getDisplayTopics(Constants.DISPLAY_TOPICS)
                }
                displayTopics(display)
                searchView.dismissDropDown()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

            val displayTopics = TopicLibrary . getDisplayTopics (Constants.DISPLAY_TOPICS)
            displayTopics(displayTopics)


        binding.btnLastTopic.setOnClickListener {
            openQuestionsHandler(User.lastTopic)
        }
        return root
    }

    private fun displayTopics(displayTopics: List<String>) {
        childFragmentManager.fragments.forEach {
            childFragmentManager.beginTransaction().remove(it).commit()
        }
        for (topicName in displayTopics) {
            val topicCardFragment = TopicCardFragment()
            val args = Bundle()
            args.putString("topicName", topicName)
            topicCardFragment.arguments = args
            childFragmentManager.beginTransaction()
                .add(binding.topFiveCategories.id, topicCardFragment)
                .commit()
        }
    }

    private fun openQuestionsHandler(selectedTopic: String) {
        val topic = TopicLibrary.getTopic(selectedTopic)
        if (topic.questions.all { it.previousAttempt == PreviousAttempt.POSITIVE }) {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationSwipeCard(
                selectedTopic,
                PreviousAttempt.POSITIVE
            )
            findNavController().navigate(action)
            return
        }
        val dialog = QuestionSelectDialogFragment()
        val args = Bundle()
        args.putString("topic", selectedTopic)
        dialog.arguments = args
        dialog.show(parentFragmentManager, "QuestionSelectDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
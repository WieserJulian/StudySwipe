package com.example.studyswipe.ui.home

import QuestionSelectDialogFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.app.PreviousAttempt
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.app.User
import com.example.studyswipe.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.d("HomeFragment", "Users Last Topic: ${User.lastTopic}")
        User.lastTopic = listOf("MATH", "CARS", "HISTORY")[2]  // TODO Remove only for test purpose
        if (User.lastTopic.isEmpty()) {
            binding.btnLastTopic.visibility = View.GONE
        } else {
            val lastTopic = getString(R.string.lastTopic, User.lastTopic)
            binding.btnLastTopic.text = lastTopic
        }
        binding.btnLastTopic.setOnClickListener() {
            openQuestionsHandler(User.lastTopic)
        }
        return root
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
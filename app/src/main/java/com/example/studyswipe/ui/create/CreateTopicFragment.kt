package com.example.studyswipe.ui.create

import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyswipe.R
import com.example.studyswipe.app.Question
import com.example.studyswipe.app.QuestionAdapter
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.databinding.FragmentCreateTopicBinding

class CreateTopicFragment : Fragment() {

    private var _binding: FragmentCreateTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val questions = mutableListOf<EditQuestionFragment>()
    private lateinit var questionAdapter: QuestionAdapter
    private var counter: Int = 0

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var editViewModel = ViewModelProvider(requireActivity()).get(EditQuestionViewModel::class.java)
        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.topicName.text = "Ausbau der Studyswipe App"

        val recyclerView = binding.createQuestionOutlet
        recyclerView.layoutManager = LinearLayoutManager(context)
        questionAdapter = QuestionAdapter(childFragmentManager, questions)
        recyclerView.adapter = questionAdapter

        // Set up the btnaddQuestion button
        binding.btnaddNewquestion.setOnClickListener {
            println("Add new question")
            // Add a new EditQuestionFragment to the list
            questions.add(EditQuestionFragment())
            counter += 1

            // Notify the adapter that the data set has changed
            questionAdapter.notifyItemInserted(questions.size - 1)
        }

        binding.btnsaveTopic.setOnClickListener {
            if (questions.isNotEmpty()) {
                val listQuestion: List<Question> = editViewModel.getAllQuestions()
                for (question in listQuestion) {
                    Log.d("CreateTopicFragment", question.question)
                }
                TopicLibrary.addTopic(binding.topicName.text.toString(), listQuestion)
            }
            findNavController().navigate(R.id.action_createTopicFragment_to_navigation_add)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
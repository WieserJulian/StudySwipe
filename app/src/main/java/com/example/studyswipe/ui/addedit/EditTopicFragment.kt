package com.example.studyswipe.ui.addedit

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.app.Question
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.databinding.FragmentCreateTopicBinding
import java.util.UUID

class EditTopicFragment : Fragment() {

    private var _binding: FragmentCreateTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var editViewModel: EditQuestionViewModel
    private var topicName = ""

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_topic_name, null)
        val editTextTopicName = dialogView.findViewById<EditText>(R.id.dialogTopicNameEditText)

        editViewModel = ViewModelProvider(requireActivity()).get(EditQuestionViewModel::class.java)
        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)
        val root: View = binding.root
        topicName = arguments?.getString("topicName") ?: ""
        if (topicName == "") {
            AlertDialog.Builder(requireContext())
                .setTitle("Set Topic Name")
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->
                    topicName = editTextTopicName.text.toString()
                    binding.topicName.text = topicName
                }
                .setNegativeButton("Cancel", null)
                .show()

        }
        binding.topicName.text = topicName
        displayQuestions(TopicLibrary.getTopic(topicName).questions)

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            editViewModel.setPreviewMode(!isChecked)
            if (isChecked) {
                binding.switch1.text = getString(R.string.editMode)
            } else {
                binding.switch1.text = getString(R.string.previewMode)
            }
        }

        binding.btnaddNewquestion.setOnClickListener {
            var questions = editViewModel.getAllQuestions()
            // TODO add Edit Dialog here and the finished question add to list
            questions.add(Question("", "", 0))
            displayQuestions(questions)
        }

        binding.btnsaveTopic.setOnClickListener {
            val questions = editViewModel.getAllQuestions()
            if (questions.isNotEmpty()) {
                val listQuestion: List<Question> = editViewModel.getAllQuestions()
                for (question in listQuestion) {
                    Log.d("CreateTopicFragment", question.question)
                }
                TopicLibrary.addTopic(binding.topicName.text.toString(), listQuestion)
                this.context?.let { it1 -> com.example.studyswipe.utils.FileUtils.saveAsJson(it1) }
            }
            findNavController().navigate(R.id.action_createTopicFragment_to_navigation_add)
        }


        return root
    }

    private fun displayQuestions(displayQuestions: List<Question>) {

        childFragmentManager.fragments.forEach {
            childFragmentManager.beginTransaction().remove(it).commit()
        }
        for (question in displayQuestions) {
            val topicCardFragment = EditQuestionFragment()
            val args = Bundle()
            val uuid: String = UUID.randomUUID().toString()
            editViewModel.addQuestion(uuid, question)
            args.putString("questionUID", uuid)
            topicCardFragment.arguments = args
            childFragmentManager.beginTransaction()
                .add(binding.questionOutlet.id, topicCardFragment)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
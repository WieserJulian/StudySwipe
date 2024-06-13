package com.example.studyswipe.ui.addedit

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        if (topicName.isEmpty()) {

            dialogView.requireViewById<EditText>(R.id.dialogTopicNameEditText).addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val input = s.toString()
                    if (TopicLibrary.exists(input)) {
                        editTextTopicName.error = "Topic already exists"
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // No action needed here
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // No action needed here
                }
            })

            AlertDialog.Builder(requireContext())
                .setTitle("Set Topic Name")
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->
                    topicName = editTextTopicName.text.toString()
                    binding.topicName.text = topicName
                    initView()
                }
                .setNegativeButton("Cancel", null)
                .show()

        } else {
            initView()
        }
        Log.d("EditTopicFragment", "Topic name: $topicName ${topicName.isEmpty()}")


        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            editViewModel.setPreviewMode(!isChecked)
            if (isChecked) {
                binding.switch1.text = getString(R.string.editMode)
            } else {
                binding.switch1.text = getString(R.string.previewMode)
            }
        }

        binding.btnaddNewquestion.setOnClickListener {
            val uuid: String = UUID.randomUUID().toString()
            val newQuestion = Question("New Question", "New Answer", 1)
            editViewModel.addQuestion(uuid, newQuestion)
            displayQuestions(editViewModel.getAllQuestionsUuid())
        }

        binding.btnsaveTopic.setOnClickListener {
            val questions = editViewModel.getAllQuestionsUuid()
            if (questions.isNotEmpty()) {
                TopicLibrary.updateQuestions(topicName, editViewModel.getAllQuestions())
                this.context?.let { it1 -> com.example.studyswipe.utils.FileUtils.saveAsJson(it1) }
            }
            findNavController().navigate(R.id.action_createTopicFragment_to_navigation_add)
        }
        return root
    }

    private fun initView(){
        binding.topicName.text = topicName
        if (TopicLibrary.exists(topicName)) {
            editViewModel.addQuestions(TopicLibrary.getTopic(topicName).questions)
        } else {
            editViewModel.addQuestions(listOf(Question("New Question", "New Answer", 1)))
        }
        displayQuestions(editViewModel.getAllQuestionsUuid())
    }

    fun displayQuestions(displayQuestions: List<Pair<String, Question>>) {

        childFragmentManager.fragments.forEach {
            childFragmentManager.beginTransaction().remove(it).commit()
        }
        for (pair in displayQuestions) {
            val uuid = pair.first
            val question = pair.second
            val topicCardFragment = EditQuestionFragment()
            val args = Bundle()
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
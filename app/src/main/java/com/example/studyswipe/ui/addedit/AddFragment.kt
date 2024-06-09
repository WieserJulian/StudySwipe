package com.example.studyswipe.ui.addedit

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.databinding.FragmentAddBinding
import com.example.studyswipe.ui.topic.TopicCardFragment
import com.example.studyswipe.utils.Constants

class AddFragment : Fragment(), TopicCardFragment.OnTopicCardClickListener {

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onTopicCardClick(topicName: String) {
        openEditQuestionHandler(topicName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val btnAddTopic = binding.btnAddTopic
        val root: View = binding.root


        val searchView = binding.searchTopics
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            TopicLibrary.getDisplayTopics(Constants.DISPLAY_TOPICS)
        )
        searchView.setAdapter(adapter)
        searchView.setOnItemClickListener { parent, view, position, id ->
            selectDisplayTopics(searchView)
        }
        searchView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                selectDisplayTopics(searchView)
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        val displayTopics = TopicLibrary.getTopicsNames()
        displayTopics(displayTopics)

        btnAddTopic.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_add_to_editTopicFragment)
        }

        return root
    }

    private fun selectDisplayTopics(searchView: AutoCompleteTextView) {
        var display = TopicLibrary.getTopicsNames().filter {
            it.contains(searchView.text.toString(), ignoreCase = true)
        }
        if (display.isEmpty()) {
            display = TopicLibrary.getTopicsNames()
        }
        displayTopics(display)
        searchView.dismissDropDown()
    }


    private fun openEditQuestionHandler(topicName: String) {
        val bundle = Bundle()
        bundle.putString("topicName", topicName)
        findNavController().navigate(R.id.action_navigation_add_to_editTopicFragment, bundle)
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
                .add(binding.displayTopics.id, topicCardFragment)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
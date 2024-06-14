package com.example.studyswipe.ui.addedit

import android.R
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.databinding.FragmentEditTopicSelectBinding
import com.example.studyswipe.ui.topic.TopicCardFragment
import com.example.studyswipe.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView

class EditSelectTopicFragment : Fragment(), TopicCardFragment.OnTopicCardClickListener {

    private var _binding: FragmentEditTopicSelectBinding? = null
    private val binding get() = _binding!!
    private var deleteTopics = false


    override fun onTopicCardClick(topicName: String) {
        openEditQuestionHandler(topicName)
    }


    private fun openEditQuestionHandler(topicName: String) {
        if (deleteTopics) {
            AlertDialog.Builder(context)
                .setTitle("Delete Topic")
                .setMessage("Are you sure you want to delete:\n$topicName?")
                .setPositiveButton("Yes") { dialog, which ->
                    TopicLibrary.deleteTopic(topicName)
                    this.context?.let { it1 -> com.example.studyswipe.utils.FileUtils.saveAsJson(it1) }
                    displayTopics(TopicLibrary.getTopicsNames())
                }
                .setNegativeButton("No") { dialog, which -> }
                .show()
            return
        }
        val bundle = Bundle()
        bundle.putString("topicName", topicName)
        findNavController().navigate(
            com.example.studyswipe.R.id.action_navigation_add_to_editTopicFragment, bundle)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTopicSelectBinding.inflate(LayoutInflater.from(context))
        val searchView = binding.searchTopics
        requireActivity().findViewById<BottomNavigationView>(com.example.studyswipe.R.id.nav_view).visibility = View.GONE
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_dropdown_item_1line,
            TopicLibrary.getDisplayTopics(Constants.DISPLAY_TOPICS)
        )
        searchView.setAdapter(adapter)
        searchView.setOnItemClickListener { parent, view, position, id ->
            selectDisplayTopics(searchView)
        }
        searchView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                selectDisplayTopics(searchView)
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        displayTopics(TopicLibrary.getTopicsNames())


        binding.deleteTopics.setOnClickListener {
            deleteTopics = !deleteTopics
            if (deleteTopics) {
                binding.deleteTopics.text = resources.getString(com.example.studyswipe.R.string.deleteTopics)
            } else {
                binding.deleteTopics.text = resources.getString(com.example.studyswipe.R.string.editTopics)
            }
        }


        return binding.root
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


    fun displayTopics(displayTopics: List<String>) {
        childFragmentManager.fragments.forEach {
            childFragmentManager.beginTransaction().remove(it).commit()
        }
        for (topicName in displayTopics) {
            val topicCardFragment = TopicCardFragment()
            val args = Bundle()
            args.putString("topicName", topicName)
            topicCardFragment.arguments = args
            childFragmentManager.beginTransaction()
                .add(binding.editTopicSelectScrollTopics.id, topicCardFragment)
                .commit()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
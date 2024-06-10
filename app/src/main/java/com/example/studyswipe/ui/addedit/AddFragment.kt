package com.example.studyswipe.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val btnAddTopic = binding.btnAddTopic
        val btnEditTopic = binding.btnEditTopic
        val root: View = binding.root

        btnEditTopic.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_add_to_navigation_edit)
        }

        btnAddTopic.setOnClickListener {

            findNavController().navigate(R.id.action_navigation_add_to_editTopicFragment)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
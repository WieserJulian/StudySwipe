package com.example.studyswipe.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.databinding.FragmentCreateTopicBinding

class CreateTopicFragment : Fragment() {

    private var _binding: FragmentCreateTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val title = CreateTopicFragmentArgs.fromBundle(requireArguments()).title

        binding.topicName.text = title

        // Handle the back button event
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            root.findNavController().navigate(R.id.navigation_add)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
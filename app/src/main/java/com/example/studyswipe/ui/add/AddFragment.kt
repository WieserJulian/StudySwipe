package com.example.studyswipe.ui.add

import android.app.AlertDialog
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.databinding.FragmentAddBinding
import com.example.studyswipe.databinding.FragmentCreateTopicBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val btnAddTopic = binding.btnAddTopic
        val root: View = binding.root

        btnAddTopic.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Enter Topic Title")

            val input = EditText(requireContext())
            builder.setView(input)

            builder.setPositiveButton("Save") { _, _ ->
                val title = input.text.toString()
                val action = AddFragmentDirections.actionNavigationAddToCreateTopicFragment(title)
                it.findNavController().navigate(action)

            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

            builder.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
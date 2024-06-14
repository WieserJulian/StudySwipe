package com.example.studyswipe.ui.swipe

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studyswipe.R
import com.example.studyswipe.app.PreviousAttempt
import com.example.studyswipe.app.Question
import com.example.studyswipe.app.TopicLibrary
import com.example.studyswipe.databinding.FragmentSwipeFinishedBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomnavigation.BottomNavigationView

class SwipeFinishedFragment : Fragment() {


    private var _binding: FragmentSwipeFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeFinishedBinding.inflate(inflater, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.GONE
        val root = binding.root


        val topicName = arguments?.getString("topic") ?: ""

        val questionType : List<Question> =  TopicLibrary.getTopic(topicName).questions

        binding.swipeFinishedTopicName.text = topicName

        val pieChart = binding.swipeFinishedPieChart


        pieChart.getDescription().setEnabled(false)
        pieChart.legend.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setDrawHoleEnabled(false)

        var categories = PreviousAttempt.entries.toList()
        categories = categories.filter { it != PreviousAttempt.RETRY } //TODO Remove this line when RETRY is implemented
        val counts = categories.map { countByPreviousAttempt(questionType, it) }
        val entries = counts.map { PieEntry(it.toFloat(), it) }

        val pieDataSet = PieDataSet(entries, "Categories")
        val colors = listOf(Color.GREEN, Color.RED, Color.YELLOW)
        pieDataSet.colors = colors
        val pieData = PieData(pieDataSet)

        pieChart.data = pieData
        pieChart.invalidate() // refresh



        binding.finishedSwipeClose.setOnClickListener() {
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
            findNavController().navigate(R.id.navigation_home)

        }

        return root
    }
    private fun countByPreviousAttempt(questions: List<Question>, category: PreviousAttempt): Int {
        return questions.count { it.previousAttempt == category } ?: 0
    }


}
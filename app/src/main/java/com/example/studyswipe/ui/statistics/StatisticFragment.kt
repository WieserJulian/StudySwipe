package com.example.studyswipe.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.studyswipe.R
import com.example.studyswipe.app.PreviousAttempt
import com.example.studyswipe.app.User
import com.example.studyswipe.databinding.FragmentStatisticBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StatisticFragment : Fragment() {

    private var _binding: FragmentStatisticBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textNotifications.text = getString(R.string.statistic_title)
        val pieChart = binding.statisticPieChart


        pieChart.getDescription().setEnabled(false)
        pieChart.legend.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setDrawHoleEnabled(false)

        val categories = PreviousAttempt.entries.toList()
        val counts = categories.map { User.getCounterByPreviousAttempt(it) }
        val entries = counts.map { PieEntry(it.toFloat(), it) }
        Log.d("SwipeFinishedFragment", "Entries: $entries")
        Log.d("SwipeFinishedFragment", "Counts: $counts")

        val pieDataSet = PieDataSet(entries, "Categories")
        val colors = listOf(Color.GREEN, Color.RED, Color.YELLOW)
        pieDataSet.colors = colors
        val pieData = PieData(pieDataSet)

        pieChart.data = pieData
        pieChart.invalidate() // refresh

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
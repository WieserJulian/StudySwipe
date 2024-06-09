package com.example.studyswipe.app
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyswipe.ui.topic.TopicCardFragment

class TopicAdapter(private val fragmentManager: FragmentManager, private val topics: MutableList<TopicCardFragment>) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(val fragment: TopicCardFragment) : RecyclerView.ViewHolder(fragment.requireView())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val topicFragment = TopicCardFragment()
        fragmentManager.beginTransaction().add(topicFragment, "topicCard${viewType}").commitNow()
        return TopicViewHolder(topicFragment)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        // You can set up your fragment here
        Log.d("TopicAdapter", "onBindViewHolder: $position")
        holder.fragment.arguments = topics[position].arguments
        holder.fragment.setUpCard()
    }

    override fun getItemCount() = topics.size
}
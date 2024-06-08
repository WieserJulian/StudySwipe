package com.example.studyswipe.app

import android.content.Context
import com.example.studyswipe.utils.FileUtils
import java.io.File

object TopicLibrary {
    var topics: ArrayList<Topic> = ArrayList()

    fun initialize(context: Context) {
        topics = FileUtils.loadFromJson(context) as ArrayList<Topic>
    }

    fun getTopicsNames(): List<String> {
        return topics.map { it.name }
    }

    fun getTopic(topicName: String): Topic {
        return topics.filter { it.name == topicName }.first()
    }

    fun addTopic(topicName: String, questions: List<Question>) {
        topics += Topic(topicName, questions)
    }

    fun removeTopic(topic: Topic) {
        topics.remove(topic)
    }

    fun updateTopic(topic: Topic) {
        removeTopic(topic)
        addTopic(topic.name, topic.questions)
    }

}


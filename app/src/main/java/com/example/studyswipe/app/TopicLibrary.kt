package com.example.studyswipe.app

import android.content.Context
import com.example.studyswipe.utils.FileUtils

object TopicLibrary {
    var topics: List<Topic> = listOf()

    fun initialize(context: Context) {
        topics = FileUtils.loadFromJson(context)
    }

    fun getTopic(): Topic {
        // TODO
        return topics[0]
    }

    fun addTopic(topicName: String, questions: List<Question>) {
        // TODO
    }

    fun removeTopic(topic: Topic) {
        // TODO
    }

    fun updateTopic(topic: Topic) {
        // TODO
    }

}


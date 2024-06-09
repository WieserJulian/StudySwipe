package com.example.studyswipe.app

object TopicLibrary {
    var topics: ArrayList<Topic> = ArrayList()

    fun getTopicsNames(): List<String> {
        return topics.map { it.name }
    }

    fun getTopic(topicName: String): Topic {
        return topics.filter { it.name == topicName }.first()
    }

    fun addTopic(
        topicName: String,
        questions: List<Question>,
        isNew: Boolean = false,
        isFavorite: Boolean = false,
        swiped: Int = 0
    ) {
        topics += Topic(topicName, questions, isNew, isFavorite, swiped)
    }

    fun removeTopic(topic: Topic) {
        topics.remove(topic)
    }

    fun getRandomTopic(): String {
        return topics.random().name
    }

    fun updateQuestions(topicName: String, allQuestion: List<Question>) {
        val topic = getTopic(topicName)
        removeTopic(topic)
        addTopic(topicName, allQuestion, topic.isNew, topic.isFavorite, topic.swiped + 1)

    }

    fun getDisplayTopics(displayTopics: Int): List<String> {
        var topicsToDisplay = mutableSetOf<String>()
        val favoriteTopics = topics.filter { it.isFavorite }.take(displayTopics)
        if (favoriteTopics.size == displayTopics) {
            return favoriteTopics.map { it.name };
        }
        topicsToDisplay.addAll(favoriteTopics.map { it.name })
        val newTopics =
            topics.filter { it.isNew || !it.isFavorite }.take(displayTopics - topicsToDisplay.size)
        if (newTopics.size == displayTopics - topicsToDisplay.size) {
            topicsToDisplay.addAll(newTopics.map { it.name })
            return topicsToDisplay.toList()
        }
        topicsToDisplay += newTopics.map { it.name }
        val otherTopics = topics.filter { !it.isFavorite && !it.isNew }.sortedBy { it.swiped }
            .take(displayTopics - topicsToDisplay.size)
        topicsToDisplay.addAll(otherTopics.map { it.name })
        return topicsToDisplay.toList()

    }

}

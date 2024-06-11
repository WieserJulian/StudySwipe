package com.example.studyswipe.app

object TopicLibrary {
    var topics: ArrayList<Topic> = ArrayList()

    fun getTopicsNames(): List<String> {
        return topics.map { it.name }
    }

    fun getTopic(topicName: String): Topic {
        if (!exists(topicName)) {
            return Topic("", listOf())
        }
        return topics.filter { it.name == topicName }.first()
    }

    fun addTopic(
        topicName: String,
        questions: List<Question>,
        isNew: Boolean = false,
        isFavorite: Boolean = false,
        swiped: Int = 0
    ) {
        val name: String

        if (topics.any { t -> t.name.equals(topicName) }) {
            name = getUniqueTopicName(topicName)
        } else {
            name = topicName
        }

        topics += Topic(name, questions, isNew, isFavorite, swiped)
    }

    private fun getUniqueTopicName(topicName: String, cnt: Int = 1): String {
        if (topics.any { t -> t.name.equals(topicName+"_"+cnt) }) {
            return getUniqueTopicName(topicName, cnt+1)
        }
        return topicName+"_"+cnt
    }

    fun exists(topicName: String): Boolean {
        return topics.any { it.name == topicName }
    }

    fun removeTopic(topic: Topic) {
        topics.remove(topic)
    }


    fun updateQuestions(topicName: String, allQuestion: List<Question>) {
        if (exists(topicName)) {
            val topic = getTopic(topicName)
            removeTopic(topic)
            addTopic(topicName, allQuestion, topic.isNew, topic.isFavorite, topic.swiped + 1)
            return
        }
        addTopic(topicName, allQuestion)


    }

    fun getDisplayTopics(displayTopics: Int): List<String> {
        var topicsToDisplay = mutableSetOf<String>()
        val favoriteTopics = topics.filter { it.isFavorite }.take(displayTopics)
        if (favoriteTopics.size == displayTopics) {
            return favoriteTopics.map { it.name }
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

    fun deleteTopic(topicName: String) {
        topics.removeIf { it.name == topicName }
    }

}

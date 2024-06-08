package com.example.studyswipe.app

object TopicLibrary {
    var topics: ArrayList<Topic> = ArrayList()

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

    fun getRandomTopic(): String {
        return topics.random().name
    }

    fun updateQuestions(topicName: String, allQuestion: List<Question>) {
        val topic = getTopic(topicName)
        removeTopic(topic)
        addTopic(topicName, allQuestion)

    }

}

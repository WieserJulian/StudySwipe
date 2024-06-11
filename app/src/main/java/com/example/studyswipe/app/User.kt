package com.example.studyswipe.app

object User {

    var positiveCounter: Int = 1
    var negativeCounter: Int = 5
    var retryCounter: Int = 3
    var points: Double = 0.0
    var lastTopic: String = ""

    fun applyQuestionResults(topicName: String, allQuestion: List<Question>) {
        positiveCounter += allQuestion.filter { it.previousAttempt == PreviousAttempt.POSITIVE }.size
        negativeCounter += allQuestion.filter { it.previousAttempt == PreviousAttempt.NEGATIVE }.size
        retryCounter += allQuestion.filter { it.previousAttempt == PreviousAttempt.RETRY }.size
        var negativePoints = allQuestion.filter { it.previousAttempt == PreviousAttempt.NEGATIVE }.sumOf { it.points.toInt()/2 }
        var positivePoints = allQuestion.filter { it.previousAttempt == PreviousAttempt.POSITIVE }.sumOf { it.points.toInt() }
        var retryPoints = allQuestion.filter { it.previousAttempt == PreviousAttempt.RETRY }.sumOf { it.points.toInt() / 2 }
        points += positivePoints - negativePoints - retryPoints
        points = points.coerceAtLeast(0.0)
        lastTopic = topicName
    }

    fun getCounterByPreviousAttempt(category: PreviousAttempt): Int {
        return when (category) {
            PreviousAttempt.POSITIVE -> positiveCounter
            PreviousAttempt.NEGATIVE -> negativeCounter
            PreviousAttempt.RETRY -> retryCounter
        }
    }
}
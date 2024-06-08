package com.example.studyswipe.app

object User {

    var positiveCounter: Int = 0
    var negativeCounter: Int = 0
    var retryCounter: Int = 0
    var points: Double = 0.0


    fun applyQuestionResults(allQuestion: List<Question>) {
        positiveCounter += allQuestion.filter { it.previousAttempt == PreviousAttempt.POSITIVE }.size
        negativeCounter += allQuestion.filter { it.previousAttempt == PreviousAttempt.NEGATIVE }.size
        retryCounter += allQuestion.filter { it.previousAttempt == PreviousAttempt.RETRY }.size
        var negativePoints = allQuestion.filter { it.previousAttempt == PreviousAttempt.NEGATIVE }.sumOf { it.points.toInt()/2 }
        var positivePoints = allQuestion.filter { it.previousAttempt == PreviousAttempt.POSITIVE }.sumOf { it.points.toInt() }
        var retryPoints = allQuestion.filter { it.previousAttempt == PreviousAttempt.RETRY }.sumOf { it.points.toInt() / 2 }
        points += positivePoints - negativePoints - retryPoints
        points = points.coerceAtLeast(0.0)
    }
}
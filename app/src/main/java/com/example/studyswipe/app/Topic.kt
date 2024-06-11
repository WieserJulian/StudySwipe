package com.example.studyswipe.app

data class Topic(
    val name: String,
    var questions: List<Question>,
    var isNew: Boolean = false,
    var isFavorite: Boolean = false,
    var swiped: Int = 0
) {
}

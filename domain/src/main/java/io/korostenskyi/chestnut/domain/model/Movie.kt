package io.korostenskyi.chestnut.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val backdropPath: String,
    val isAdult: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
package io.korostenskyi.chestnut.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val voteAverage: Double,
    val voteCount: Int,
    val isAdult: Boolean
)
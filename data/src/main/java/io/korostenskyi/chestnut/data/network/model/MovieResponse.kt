package io.korostenskyi.chestnut.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("overview") val description: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("adult") val isAdult: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

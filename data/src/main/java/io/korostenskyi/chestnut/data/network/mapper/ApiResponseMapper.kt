package io.korostenskyi.chestnut.data.network.mapper

import io.korostenskyi.chestnut.data.network.model.MovieResponse
import io.korostenskyi.chestnut.domain.model.Movie
import javax.inject.Inject

class ApiResponseMapper @Inject constructor() {

    fun map(response: MovieResponse): Movie {
        return Movie(
            id = response.id,
            title = response.title,
            description = response.description,
            posterPath = "$POSTER_BASE_URL${response.posterPath}",
            backdropPath = response.backdropPath,
            isAdult = response.isAdult,
            voteAverage = response.voteAverage,
            voteCount = response.voteCount
        )
    }

    companion object {
        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185_and_h278_bestv2"
    }
}

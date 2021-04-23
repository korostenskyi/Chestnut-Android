package io.korostenskyi.chestnut.data.network

import io.korostenskyi.chestnut.data.network.model.MovieDetailsResponse
import io.korostenskyi.chestnut.data.network.model.PopularMoviesResponse

interface MovieDataSource {

    suspend fun fetchPopularMovies(page: Int): PopularMoviesResponse

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse
}

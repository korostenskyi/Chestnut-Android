package io.korostenskyi.chestnut.domain.repository

import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.domain.model.MovieDetails

interface MovieRepository {

    suspend fun fetchPopularMovies(page: Int): List<Movie>

    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
}

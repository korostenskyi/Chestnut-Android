package io.korostenskyi.chestnut.domain.interactor

import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.domain.model.MovieDetails

interface MovieInteractor {

    suspend fun fetchPopularMovies(page: Int): List<Movie>

    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
}

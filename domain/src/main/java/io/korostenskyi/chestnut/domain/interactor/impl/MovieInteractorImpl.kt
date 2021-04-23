package io.korostenskyi.chestnut.domain.interactor.impl

import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.domain.model.MovieDetails
import io.korostenskyi.chestnut.domain.repository.MovieRepository
import javax.inject.Inject

class MovieInteractorImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieInteractor {

    override suspend fun fetchPopularMovies(page: Int): List<Movie> {
        return movieRepository.fetchPopularMovies(page)
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return movieRepository.fetchMovieDetails(movieId)
    }
}

package io.korostenskyi.chestnut.domain.interactor.impl

import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.domain.repository.MovieRepository
import javax.inject.Inject

class MovieInteractorImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieInteractor {

    override suspend fun fetchPopularMovies(): List<Movie> {
        return movieRepository.fetchPopularMovies()
    }
}

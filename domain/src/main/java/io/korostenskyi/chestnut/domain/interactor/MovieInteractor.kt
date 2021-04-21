package io.korostenskyi.chestnut.domain.interactor

import io.korostenskyi.chestnut.domain.model.Movie

interface MovieInteractor {

    suspend fun fetchPopularMovies(page: Int): List<Movie>
}

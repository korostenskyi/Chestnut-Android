package io.korostenskyi.chestnut.domain.repository

import io.korostenskyi.chestnut.domain.model.Movie

interface MovieRepository {

    suspend fun fetchPopularMovies(page: Int): List<Movie>
}

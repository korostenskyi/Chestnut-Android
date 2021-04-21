package io.korostenskyi.chestnut.presentation.screen.popular

import io.korostenskyi.chestnut.domain.model.Movie

sealed class MoviesState {

    object Loading : MoviesState()

    data class Success(val movies: List<Movie>) : MoviesState()

    data class Failure(val message: String) : MoviesState()
}

package io.korostenskyi.chestnut.presentation.screen.details

import io.korostenskyi.chestnut.domain.model.MovieDetails

sealed class MovieDetailsState {

    object Loading : MovieDetailsState()

    data class Success(val movie: MovieDetails) : MovieDetailsState()

    data class Error(val message: String) : MovieDetailsState()
}

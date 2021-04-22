package io.korostenskyi.chestnut.presentation.screen.details

sealed class MovieDetailsState {

    object Loading : MovieDetailsState()

    data class Success(val movie: String) : MovieDetailsState()

    data class Error(val message: String) : MovieDetailsState()
}

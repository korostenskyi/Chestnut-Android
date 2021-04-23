package io.korostenskyi.chestnut.presentation.routing

interface Router {

    fun navigate(navigator: (Router) -> Unit)

    fun back()

    fun fromPopularToMovieDetails(movieId: Int)
}

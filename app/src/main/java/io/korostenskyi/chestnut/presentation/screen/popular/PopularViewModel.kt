package io.korostenskyi.chestnut.presentation.screen.popular

import androidx.hilt.lifecycle.ViewModelInject
import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.extensions.launch
import io.korostenskyi.chestnut.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PopularViewModel @ViewModelInject constructor(
    private val movieInteractor: MovieInteractor
): BaseViewModel() {

    private val _popularMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())

    val popularMoviesFlow: StateFlow<List<Movie>> = _popularMoviesFlow
    var page = 1
        set(value) {
            retrievePopularMovies()
            field = value
        }

    fun retrievePopularMovies() = launch {
        val movies = movieInteractor.fetchPopularMovies(page)
        _popularMoviesFlow.value = movies
    }
}

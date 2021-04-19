package io.korostenskyi.chestnut.presentation.screen.popular

import dagger.hilt.android.lifecycle.HiltViewModel
import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.extensions.launch
import io.korostenskyi.chestnut.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
): BaseViewModel() {

    private val _moviesStateFlow = MutableStateFlow<MoviesState>(MoviesState.Loading)

    val moviesStateFlow: StateFlow<MoviesState>
        get() = _moviesStateFlow

    private val movies = mutableListOf<Movie>()

    var page = 1
        set(value) {
            field = value
            retrievePopularMovies()
        }

    fun retrievePopularMovies() = launch {
        _moviesStateFlow.value = MoviesState.Loading
        val result = movieInteractor.fetchPopularMovies(page)
        movies.addAll(result)
        _moviesStateFlow.value = MoviesState.Success(movies)
    }
}

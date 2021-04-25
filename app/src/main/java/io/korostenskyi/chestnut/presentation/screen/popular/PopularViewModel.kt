package io.korostenskyi.chestnut.presentation.screen.popular

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
): BaseViewModel() {

    private val _moviesStateFlow = MutableStateFlow<MoviesState>(MoviesState.Loading)

    val moviesStateFlow: StateFlow<MoviesState>
        get() = _moviesStateFlow

    private var moviesJob: Job? = null

    private val movies = mutableListOf<Movie>()
    private var page = 0

    fun loadMore() {
        page += 1
        retrievePopularMovies(page)
    }

    private fun retrievePopularMovies(page: Int) {
        moviesJob = viewModelScope.launch {
            _moviesStateFlow.value = MoviesState.Loading
            val result = movieInteractor.fetchPopularMovies(page)
            movies.addAll(result)
            _moviesStateFlow.value = MoviesState.Success(movies)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesJob?.cancel()
    }
}

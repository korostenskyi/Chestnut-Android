package io.korostenskyi.chestnut.presentation.screen.popular

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
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

    private var moviesJob: Job? = null

    private val _moviesStateFlow = MutableStateFlow<MoviesState>(MoviesState.Loading)

    val moviesStateFlow: StateFlow<MoviesState>
        get() = _moviesStateFlow

    var page = 1

    fun retrievePopularMovies(page: Int) {
        moviesJob = viewModelScope.launch {
            _moviesStateFlow.value = MoviesState.Loading
            val result = movieInteractor.fetchPopularMovies(page)
            _moviesStateFlow.value = MoviesState.Success(result)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesJob?.cancel()
    }
}

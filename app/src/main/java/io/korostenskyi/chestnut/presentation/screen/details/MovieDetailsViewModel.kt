package io.korostenskyi.chestnut.presentation.screen.details

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
import io.korostenskyi.chestnut.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
) : BaseViewModel() {

    private val _detailsFlow = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)

    val detailsFlow: StateFlow<MovieDetailsState>
        get() = _detailsFlow

    private var movieDetailsJob: Job? = null

    fun retrieveMovieDetails(movieId: Int) {
        movieDetailsJob = viewModelScope.launch {
            _detailsFlow.value = MovieDetailsState.Loading
            delay(1000)
            _detailsFlow.value = MovieDetailsState.Success("mock")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailsJob?.cancel()
    }
}

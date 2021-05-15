package io.korostenskyi.chestnut.presentation.screen.popular

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val pagingSourceFactory: PopularPagingSource.Factory
) : BaseViewModel() {

    val moviesFlow: StateFlow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        pagingSourceFactory.create()
    }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}

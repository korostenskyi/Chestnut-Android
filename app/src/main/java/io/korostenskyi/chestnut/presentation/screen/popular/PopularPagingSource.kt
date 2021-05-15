package io.korostenskyi.chestnut.presentation.screen.popular

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.korostenskyi.chestnut.domain.interactor.MovieInteractor
import io.korostenskyi.chestnut.domain.model.Movie

class PopularPagingSource @AssistedInject constructor(
    private val movieInteractor: MovieInteractor
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(20)
        return try {
            val movies = movieInteractor.fetchPopularMovies(page)
            val nextKey = if (movies.count() < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(movies, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): PopularPagingSource
    }
}

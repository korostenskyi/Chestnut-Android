package io.korostenskyi.chestnut.data.network.impl

import io.korostenskyi.chestnut.data.network.MovieDataSource
import io.korostenskyi.chestnut.data.network.api.TMDBApi
import io.korostenskyi.chestnut.data.network.model.PopularMoviesResponse
import io.korostenskyi.chestnut.domain.model.BuildParams
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val api: TMDBApi,
    private val params: BuildParams
) : MovieDataSource {

    override suspend fun fetchPopularMovies(page: Int): PopularMoviesResponse {
        return api.fetchPopularMovies(params.tmdbApiKey, page)
    }
}

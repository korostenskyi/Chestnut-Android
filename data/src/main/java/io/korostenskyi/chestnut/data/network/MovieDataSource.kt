package io.korostenskyi.chestnut.data.network

import io.korostenskyi.chestnut.data.network.model.PopularMoviesResponse

interface MovieDataSource {

    suspend fun fetchPopularMovies(): PopularMoviesResponse
}

package io.korostenskyi.chestnut.data.network.api

import io.korostenskyi.chestnut.data.network.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): PopularMoviesResponse
}

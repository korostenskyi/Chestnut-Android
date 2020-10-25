package io.korostenskyi.chestnut.data.repository

import io.korostenskyi.chestnut.data.network.MovieDataSource
import io.korostenskyi.chestnut.data.network.mapper.ApiResponseMapper
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
    private val mapper: ApiResponseMapper
) : MovieRepository {

    override suspend fun fetchPopularMovies(): List<Movie> {
        val response = dataSource.fetchPopularMovies()
        val movies = response.movies.map { mapper.map(it) }
        return movies
    }
}

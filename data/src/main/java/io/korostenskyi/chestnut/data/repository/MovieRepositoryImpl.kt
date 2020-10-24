package io.korostenskyi.chestnut.data.repository

import android.util.Log
import io.korostenskyi.chestnut.data.network.MovieDataSource
import io.korostenskyi.chestnut.domain.model.Movie
import io.korostenskyi.chestnut.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource
) : MovieRepository {

    override suspend fun fetchPopularMovies(): List<Movie> {
        dataSource.fetchPopularMovies().also { Log.d("KURWA", it.toString()) }
        return emptyList()
    }
}

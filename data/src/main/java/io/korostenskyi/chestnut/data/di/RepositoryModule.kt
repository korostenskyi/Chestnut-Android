package io.korostenskyi.chestnut.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.korostenskyi.chestnut.data.repository.MovieRepositoryImpl
import io.korostenskyi.chestnut.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}

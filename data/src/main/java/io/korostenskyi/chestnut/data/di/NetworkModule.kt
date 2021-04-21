package io.korostenskyi.chestnut.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.korostenskyi.chestnut.data.network.MovieDataSource
import io.korostenskyi.chestnut.data.network.api.TMDBApi
import io.korostenskyi.chestnut.data.network.impl.MovieDataSourceImpl
import io.korostenskyi.chestnut.domain.model.BuildParams
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun provideMovieDataSource(impl: MovieDataSourceImpl): MovieDataSource

    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(params: BuildParams): TMDBApi {
            val client = OkHttpClient.Builder()
                    .addInterceptor(
                            HttpLoggingInterceptor().apply {
                                if (params.isDebug) HttpLoggingInterceptor.Level.BODY
                                else HttpLoggingInterceptor.Level.NONE
                            }
                    )
                    .build()
            val contentType = "application/json".toMediaType()
            return Retrofit.Builder()
                    .baseUrl(params.tmdbApiBaseUrl)
                    .client(client)
                    .addConverterFactory(
                        Json {
                            ignoreUnknownKeys = true
                        }.asConverterFactory(contentType)
                    )
                    .build()
                    .create(TMDBApi::class.java)
        }
    }
}

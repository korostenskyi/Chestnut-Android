package io.korostenskyi.chestnut.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.korostenskyi.chestnut.BuildConfig
import io.korostenskyi.chestnut.domain.model.BuildParams
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBuildParams(): BuildParams {
        return BuildParams(
            isDebug = BuildConfig.DEBUG,
            tmdbApiBaseUrl = BuildConfig.TMDB_BASE_URL,
            tmdbApiKey = BuildConfig.TMDB_API_KEY
        )
    }
}

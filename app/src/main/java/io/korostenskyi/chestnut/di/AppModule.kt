package io.korostenskyi.chestnut.di

import android.content.Context
import android.os.Build
import android.util.Log
import coil.ImageLoader
import coil.util.DebugLogger
import coil.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.korostenskyi.chestnut.BuildConfig
import io.korostenskyi.chestnut.domain.model.BuildParams
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    @Singleton
    fun provideHardwareImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
        params: BuildParams
    ): ImageLoader {
        return imageLoaderBuilder(context, okHttpClient, params).build()
    }

    private fun imageLoaderBuilder(
        context: Context,
        okHttpClient: OkHttpClient,
        params: BuildParams
    ): ImageLoader.Builder {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .logger(
                if (params.isDebug) DebugLogger()
                else coilReleaseLogger
            )
            .allowRgb565(Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
    }

    private val coilReleaseLogger by lazy {
        object : Logger {

            override var level: Int = Log.INFO

            override fun log(
                tag: String,
                priority: Int,
                message: String?,
                throwable: Throwable?
            ) {}
        }
    }
}

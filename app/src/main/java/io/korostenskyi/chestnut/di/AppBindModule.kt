package io.korostenskyi.chestnut.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.korostenskyi.chestnut.util.ui.IntentActionUtil
import io.korostenskyi.chestnut.util.ui.impl.IntentActionUtilImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppBindModule {

    @Binds
    @Singleton
    fun bindIntentActionUtil(impl: IntentActionUtilImpl): IntentActionUtil
}

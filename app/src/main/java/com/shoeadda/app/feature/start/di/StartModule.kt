package com.shoeadda.app.feature.start.di

import com.shoeadda.app.feature.start.data.locale.AndroidLocaleController
import com.shoeadda.app.feature.start.data.repository.StartRepositoryImpl
import com.shoeadda.app.feature.start.domain.repository.LocaleController
import com.shoeadda.app.feature.start.domain.repository.StartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StartModule {
    @Binds
    @Singleton
    abstract fun bindStartRepository(impl: StartRepositoryImpl): StartRepository

    @Binds
    @Singleton
    abstract fun bindLocaleController(impl: AndroidLocaleController): LocaleController
}

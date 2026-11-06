package com.shoeadda.app.feature.catalog.di

import com.shoeadda.app.core.database.AppDatabase
import com.shoeadda.app.feature.catalog.data.local.ShoeDao
import com.shoeadda.app.feature.catalog.data.remote.DummyJsonApi
import com.shoeadda.app.feature.catalog.data.repository.CatalogRepositoryImpl
import com.shoeadda.app.feature.catalog.domain.repository.CatalogRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
abstract class CatalogBindingsModule {
    @Binds
    @Singleton
    abstract fun bindCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CatalogProvidersModule {
    @Provides
    fun provideShoeDao(database: AppDatabase): ShoeDao = database.shoeDao()

    @Provides
    @Singleton
    fun provideDummyJsonApi(retrofit: Retrofit): DummyJsonApi = retrofit.create()
}

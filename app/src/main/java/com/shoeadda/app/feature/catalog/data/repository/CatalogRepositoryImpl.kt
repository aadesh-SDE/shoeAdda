package com.shoeadda.app.feature.catalog.data.repository

import com.shoeadda.app.feature.catalog.data.local.LocalShoeDataSource
import com.shoeadda.app.feature.catalog.data.local.ShoeDao
import com.shoeadda.app.feature.catalog.data.local.ShoeEntity
import com.shoeadda.app.feature.catalog.data.mapper.toDomain
import com.shoeadda.app.feature.catalog.data.mapper.toEntity
import com.shoeadda.app.feature.catalog.data.remote.CatalogRemoteDataSource
import com.shoeadda.app.feature.catalog.domain.model.RefreshResult
import com.shoeadda.app.feature.catalog.domain.model.Shoe
import com.shoeadda.app.feature.catalog.domain.repository.CatalogRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatalogRepositoryImpl @Inject constructor(
    private val dao: ShoeDao,
    private val localSource: LocalShoeDataSource,
    private val remoteSource: CatalogRemoteDataSource,
) : CatalogRepository {

    override fun observeShoes(): Flow<List<Shoe>> =
        dao.observeAll().map { rows -> rows.map(ShoeEntity::toDomain) }

    override fun observeShoe(id: String): Flow<Shoe?> =
        dao.observeById(id).map { it?.toDomain() }

    override suspend fun refresh(): RefreshResult {
        val bundled = localSource.readShoes().map { it.toEntity() }

        var onlineFailed = false
        val online = try {
            remoteSource.fetchShoes().map { it.toEntity() }
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (@Suppress("TooGenericExceptionCaught") error: Exception) {
            // No internet or the site is down: keep going with the bundled shoes.
            onlineFailed = true
            emptyList()
        }

        dao.upsertAll(bundled + online)
        return RefreshResult(bundledCount = bundled.size, onlineCount = online.size, onlineFailed = onlineFailed)
    }
}

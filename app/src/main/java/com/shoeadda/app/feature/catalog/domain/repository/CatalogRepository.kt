package com.shoeadda.app.feature.catalog.domain.repository

import com.shoeadda.app.feature.catalog.domain.model.RefreshResult
import com.shoeadda.app.feature.catalog.domain.model.Shoe
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun observeShoes(): Flow<List<Shoe>>

    /** Emits null when no shoe has that id. */
    fun observeShoe(id: String): Flow<Shoe?>

    /** Loads the bundled shoes and the online ones into the local database. Safe to call again. */
    suspend fun refresh(): RefreshResult
}

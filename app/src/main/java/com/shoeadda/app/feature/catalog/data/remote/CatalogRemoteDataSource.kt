package com.shoeadda.app.feature.catalog.data.remote

import javax.inject.Inject

class CatalogRemoteDataSource @Inject constructor(
    private val api: DummyJsonApi,
) {
    /** Men's and women's shoes from DummyJSON. Throws if the network is not available. */
    suspend fun fetchShoes(): List<ProductDto> =
        DummyJsonApi.SHOE_CATEGORIES.flatMap { api.getCategory(it).products }
}

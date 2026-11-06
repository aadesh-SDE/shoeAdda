package com.shoeadda.app.feature.catalog.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** DummyJSON, a free fake shop API. No key is needed. */
interface DummyJsonApi {
    @GET("products/category/{category}")
    suspend fun getCategory(
        @Path("category") category: String,
        @Query("limit") limit: Int = 0,
    ): ProductsResponseDto

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
        val SHOE_CATEGORIES = listOf("mens-shoes", "womens-shoes")
    }
}

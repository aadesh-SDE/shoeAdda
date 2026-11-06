package com.shoeadda.app.feature.catalog.data.remote

import kotlinx.serialization.Serializable

/** The shape of DummyJSON's answer for a category. Only the fields we use are listed; the rest are ignored. */
@Serializable
data class ProductsResponseDto(
    val products: List<ProductDto> = emptyList(),
)

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String = "",
    val category: String = "",
    /** Price in US dollars. */
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val brand: String? = null,
    val images: List<String> = emptyList(),
    val thumbnail: String? = null,
)

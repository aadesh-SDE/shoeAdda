package com.shoeadda.app.feature.catalog.data.local

import kotlinx.serialization.Serializable

/** The shape of `assets/products.json`, the shoes that ship inside the app. */
@Serializable
data class LocalShoesFileDto(
    val shoes: List<LocalShoeDto> = emptyList(),
)

@Serializable
data class LocalShoeDto(
    val id: String,
    /** File name inside `assets/shoes/`. */
    val image: String,
    val name: String,
    val brand: String,
    val gender: String,
    val style: String,
    val activity: String,
    val footWidth: String,
    val description: String,
    val priceUsd: Double,
    val discountPercent: Int = 0,
    val rating: Double,
    val reviewCount: Int,
    val colors: List<String>,
    val sizeFrom: Int,
    val sizeTo: Int,
    val soldOut: List<Int> = emptyList(),
    val isNew: Boolean = false,
)

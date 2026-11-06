package com.shoeadda.app.feature.catalog.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * One row of the `shoes` table. Lists are stored as simple text to keep the table flat:
 * [images] joined by `|`, [colors] joined by `,`, [sizes] as `uk:stock` pairs joined by `,` (for example `6:3,7:0`).
 */
@Entity(tableName = "shoes")
data class ShoeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val gender: String,
    val style: String,
    val activity: String,
    val footWidth: String,
    val description: String,
    val priceInr: Int,
    val originalPriceInr: Int,
    val discountPercent: Int,
    val rating: Float,
    val reviewCount: Int,
    val images: String,
    val colors: String,
    val sizes: String,
    val isNew: Boolean,
)

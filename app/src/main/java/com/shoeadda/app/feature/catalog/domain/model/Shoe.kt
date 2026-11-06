package com.shoeadda.app.feature.catalog.domain.model

enum class Gender { Men, Women }

enum class ShoeStyle { Sneakers, Running, Formal, Loafers, Boots, Heels, Sandals, Casual }

enum class Activity { Running, Walking, Formal }

enum class FootWidth { Narrow, Regular, Wide }

/** One UK size of a shoe and how many pairs are left. A stock of 0 means sold out. */
data class ShoeSize(
    val uk: Int,
    val stock: Int,
) {
    val available: Boolean get() = stock > 0
}

/** A shoe as the rest of the app sees it. Prices are in rupees. */
data class Shoe(
    val id: String,
    val name: String,
    val brand: String,
    val gender: Gender,
    val style: ShoeStyle,
    val activity: Activity,
    val footWidth: FootWidth,
    val description: String,
    val priceInr: Int,
    val originalPriceInr: Int,
    val discountPercent: Int,
    val rating: Float,
    val reviewCount: Int,
    /** Image addresses: web links, or `file:///android_asset/...` for photos bundled in the app. */
    val images: List<String>,
    val colors: List<String>,
    val sizes: List<ShoeSize>,
    val isNew: Boolean,
) {
    val thumbnail: String get() = images.firstOrNull().orEmpty()
    val inStock: Boolean get() = sizes.any { it.available }
}

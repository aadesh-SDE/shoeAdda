package com.shoeadda.app.feature.catalog.domain.model

/** What the user has chosen in search and the filter sheet. An empty set means "any". */
data class ShoeFilter(
    val query: String = "",
    val genders: Set<Gender> = emptySet(),
    val styles: Set<ShoeStyle> = emptySet(),
    val brands: Set<String> = emptySet(),
    val ukSizes: Set<Int> = emptySet(),
    val colors: Set<String> = emptySet(),
    val footWidths: Set<FootWidth> = emptySet(),
    val activities: Set<Activity> = emptySet(),
    val minPriceInr: Int? = null,
    val maxPriceInr: Int? = null,
    val inStockOnly: Boolean = false,
)

enum class SortOption { Popularity, PriceLowToHigh, PriceHighToLow, Newest, Discount }

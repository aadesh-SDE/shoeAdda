package com.shoeadda.app.feature.catalog.domain.usecase

import com.shoeadda.app.feature.catalog.domain.model.RefreshResult
import com.shoeadda.app.feature.catalog.domain.model.Shoe
import com.shoeadda.app.feature.catalog.domain.model.ShoeFilter
import com.shoeadda.app.feature.catalog.domain.model.SortOption
import com.shoeadda.app.feature.catalog.domain.repository.CatalogRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveShoes @Inject constructor(
    private val repository: CatalogRepository,
) {
    operator fun invoke(): Flow<List<Shoe>> = repository.observeShoes()
}

class ObserveShoe @Inject constructor(
    private val repository: CatalogRepository,
) {
    operator fun invoke(id: String): Flow<Shoe?> = repository.observeShoe(id)
}

class RefreshCatalog @Inject constructor(
    private val repository: CatalogRepository,
) {
    suspend operator fun invoke(): RefreshResult = repository.refresh()
}

/** Search, filter and sort a list of shoes. Works on the list already loaded from the database. */
class FilterAndSortShoes @Inject constructor() {

    operator fun invoke(shoes: List<Shoe>, filter: ShoeFilter, sort: SortOption): List<Shoe> =
        shoes.filter { matches(it, filter) }.sortedWith(comparatorFor(sort))

    private fun matches(shoe: Shoe, filter: ShoeFilter): Boolean {
        val query = filter.query.trim()
        if (query.isNotEmpty()) {
            val text = "${shoe.name} ${shoe.brand} ${shoe.style} ${shoe.gender} ${shoe.colors.joinToString(" ")}"
            if (!text.contains(query, ignoreCase = true)) return false
        }
        if (filter.genders.isNotEmpty() && shoe.gender !in filter.genders) return false
        if (filter.styles.isNotEmpty() && shoe.style !in filter.styles) return false
        if (filter.brands.isNotEmpty() && shoe.brand !in filter.brands) return false
        if (filter.footWidths.isNotEmpty() && shoe.footWidth !in filter.footWidths) return false
        if (filter.activities.isNotEmpty() && shoe.activity !in filter.activities) return false
        if (filter.colors.isNotEmpty() && shoe.colors.none { c -> filter.colors.any { it.equals(c, ignoreCase = true) } }) return false
        if (filter.ukSizes.isNotEmpty() && shoe.sizes.none { it.available && it.uk in filter.ukSizes }) return false
        if (filter.minPriceInr != null && shoe.priceInr < filter.minPriceInr) return false
        if (filter.maxPriceInr != null && shoe.priceInr > filter.maxPriceInr) return false
        if (filter.inStockOnly && !shoe.inStock) return false
        return true
    }

    private fun comparatorFor(sort: SortOption): Comparator<Shoe> = when (sort) {
        SortOption.Popularity -> compareByDescending<Shoe> { it.rating * it.reviewCount }.thenBy { it.name }
        SortOption.PriceLowToHigh -> compareBy<Shoe> { it.priceInr }.thenBy { it.name }
        SortOption.PriceHighToLow -> compareByDescending<Shoe> { it.priceInr }.thenBy { it.name }
        SortOption.Newest -> compareByDescending<Shoe> { it.isNew }.thenBy { it.name }
        SortOption.Discount -> compareByDescending<Shoe> { it.discountPercent }.thenBy { it.name }
    }
}

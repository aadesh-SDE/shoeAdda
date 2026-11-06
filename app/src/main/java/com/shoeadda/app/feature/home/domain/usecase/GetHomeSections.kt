package com.shoeadda.app.feature.home.domain.usecase

import com.shoeadda.app.feature.catalog.domain.model.Shoe
import com.shoeadda.app.feature.catalog.domain.usecase.ObserveShoes
import com.shoeadda.app.feature.home.domain.model.Brand
import com.shoeadda.app.feature.home.domain.model.HomeSections
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Builds the Home screen sections from the catalog. Another feature's domain is used here, never its data layer. */
class GetHomeSections @Inject constructor(
    private val observeShoes: ObserveShoes,
) {
    operator fun invoke(): Flow<HomeSections> = observeShoes().map { shoes -> build(shoes) }

    private fun build(shoes: List<Shoe>): HomeSections {
        val brands = shoes
            .groupBy { it.brand }
            .map { (name, group) -> Brand(name = name, shoeCount = group.size) }
            .sortedWith(compareByDescending<Brand> { it.shoeCount }.thenBy { it.name })
            .take(MAX_BRANDS)

        val newArrivals = shoes
            .filter { it.isNew && it.inStock }
            .sortedByDescending { it.rating }
            .take(MAX_SECTION_SHOES)

        val trending = shoes
            .filter { it.inStock }
            .sortedByDescending { it.rating * it.reviewCount }
            .take(MAX_SECTION_SHOES)

        return HomeSections(brands = brands, newArrivals = newArrivals, trending = trending)
    }

    private companion object {
        const val MAX_BRANDS = 8
        const val MAX_SECTION_SHOES = 6
    }
}

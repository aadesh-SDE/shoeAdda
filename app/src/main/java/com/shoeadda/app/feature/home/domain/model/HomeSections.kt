package com.shoeadda.app.feature.home.domain.model

import com.shoeadda.app.feature.catalog.domain.model.Shoe

data class Brand(
    val name: String,
    val shoeCount: Int,
)

/** What the Home screen shows below the banner. */
data class HomeSections(
    val brands: List<Brand>,
    val newArrivals: List<Shoe>,
    val trending: List<Shoe>,
) {
    val isEmpty: Boolean get() = newArrivals.isEmpty() && trending.isEmpty()
}

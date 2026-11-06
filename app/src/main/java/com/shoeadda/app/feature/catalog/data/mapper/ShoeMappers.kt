package com.shoeadda.app.feature.catalog.data.mapper

import com.shoeadda.app.feature.catalog.data.local.LocalShoeDto
import com.shoeadda.app.feature.catalog.data.local.ShoeEntity
import com.shoeadda.app.feature.catalog.data.remote.ProductDto
import com.shoeadda.app.feature.catalog.domain.model.Activity
import com.shoeadda.app.feature.catalog.domain.model.FootWidth
import com.shoeadda.app.feature.catalog.domain.model.Gender
import com.shoeadda.app.feature.catalog.domain.model.Shoe
import com.shoeadda.app.feature.catalog.domain.model.ShoeSize
import com.shoeadda.app.feature.catalog.domain.model.ShoeStyle
import kotlin.math.roundToInt

/** Fixed exchange rate used to show dollar prices in rupees. Change it here only. */
internal const val USD_TO_INR = 85

private const val ASSET_PREFIX = "file:///android_asset/shoes/"

// DTO -> Entity ---------------------------------------------------------------------------------

/** A shoe from DummyJSON. It has no sizes, colors or foot width, so those are made up from the shoe's id. */
internal fun ProductDto.toEntity(): ShoeEntity {
    val gender = if (category.startsWith("womens")) Gender.Women else Gender.Men
    val style = styleFromTitle(title)
    val discount = discountPercentage.roundToInt().coerceIn(0, 90)
    val currentInr = (price * USD_TO_INR).roundToInt()
    val originalInr = if (discount > 0) (currentInr * 100.0 / (100 - discount)).roundToInt() else currentInr

    return ShoeEntity(
        id = "dj-$id",
        name = title,
        brand = brand?.takeIf { it.isNotBlank() } ?: "Generic",
        gender = gender.name,
        style = style.name,
        activity = activityFor(style, title).name,
        footWidth = FootWidth.entries[id % FootWidth.entries.size].name,
        description = description,
        priceInr = currentInr,
        originalPriceInr = originalInr,
        discountPercent = discount,
        rating = (rating * 10).roundToInt() / 10f,
        reviewCount = 40 + (id * 17) % 160,
        images = images.ifEmpty { listOfNotNull(thumbnail) }.joinToString("|"),
        colors = colorsFromTitle(title).joinToString(","),
        sizes = encodeSizes(sizesFor(id, gender)),
        isNew = false,
    )
}

/** A shoe from `products.json`. */
internal fun LocalShoeDto.toEntity(): ShoeEntity {
    val currentInr = (priceUsd * USD_TO_INR).roundToInt()
    val originalInr = if (discountPercent > 0) (currentInr * 100.0 / (100 - discountPercent)).roundToInt() else currentInr
    val seed = id.hashCode() and 0x7fffffff
    val sizes = (sizeFrom..sizeTo).map { uk ->
        ShoeSize(uk = uk, stock = if (uk in soldOut) 0 else 2 + (seed + uk * 5) % 9)
    }

    return ShoeEntity(
        id = "local-$id",
        name = name,
        brand = brand,
        gender = gender,
        style = style,
        activity = activity,
        footWidth = footWidth,
        description = description,
        priceInr = currentInr,
        originalPriceInr = originalInr,
        discountPercent = discountPercent,
        rating = rating.toFloat(),
        reviewCount = reviewCount,
        images = ASSET_PREFIX + image.substringAfterLast('/'),
        colors = colors.joinToString(","),
        sizes = encodeSizes(sizes),
        isNew = isNew,
    )
}

// Entity -> Domain ------------------------------------------------------------------------------

internal fun ShoeEntity.toDomain(): Shoe = Shoe(
    id = id,
    name = name,
    brand = brand,
    gender = enumOrDefault(gender, Gender.Men),
    style = enumOrDefault(style, ShoeStyle.Casual),
    activity = enumOrDefault(activity, Activity.Walking),
    footWidth = enumOrDefault(footWidth, FootWidth.Regular),
    description = description,
    priceInr = priceInr,
    originalPriceInr = originalPriceInr,
    discountPercent = discountPercent,
    rating = rating,
    reviewCount = reviewCount,
    images = images.split('|').filter { it.isNotBlank() },
    colors = colors.split(',').filter { it.isNotBlank() },
    sizes = decodeSizes(sizes),
    isNew = isNew,
)

// Helpers ---------------------------------------------------------------------------------------

private inline fun <reified T : Enum<T>> enumOrDefault(name: String, default: T): T =
    enumValues<T>().firstOrNull { it.name.equals(name, ignoreCase = true) } ?: default

private fun encodeSizes(sizes: List<ShoeSize>): String = sizes.joinToString(",") { "${it.uk}:${it.stock}" }

private fun decodeSizes(text: String): List<ShoeSize> = text.split(',')
    .filter { it.contains(':') }
    .map { ShoeSize(uk = it.substringBefore(':').toInt(), stock = it.substringAfter(':').toInt()) }

private fun sizesFor(seed: Int, gender: Gender): List<ShoeSize> {
    val range = if (gender == Gender.Men) 6..11 else 3..8
    return range.map { uk ->
        ShoeSize(uk = uk, stock = if ((seed + uk) % 5 == 0) 0 else 2 + (seed * 3 + uk * 7) % 9)
    }
}

private fun styleFromTitle(title: String): ShoeStyle {
    val t = title.lowercase()
    return when {
        "heel" in t -> ShoeStyle.Heels
        "slipper" in t || "sandal" in t -> ShoeStyle.Sandals
        "boot" in t -> ShoeStyle.Boots
        "cleat" in t || "trainer" in t || "sneaker" in t || "jordan" in t -> ShoeStyle.Sneakers
        else -> ShoeStyle.Casual
    }
}

private fun activityFor(style: ShoeStyle, title: String): Activity {
    val t = title.lowercase()
    return when {
        style == ShoeStyle.Heels || style == ShoeStyle.Formal -> Activity.Formal
        "sport" in t || "trainer" in t || "cleat" in t -> Activity.Running
        else -> Activity.Walking
    }
}

private val colorWords = mapOf(
    "black" to "Black", "white" to "White", "red" to "Red", "blue" to "Blue", "brown" to "Brown",
    "golden" to "Gold", "gold" to "Gold", "green" to "Green", "pink" to "Pink", "grey" to "Grey",
)

private fun colorsFromTitle(title: String): List<String> {
    val words = title.lowercase().split(' ', '&').filter { it.isNotBlank() }
    return words.mapNotNull { colorWords[it] }.distinct().ifEmpty { listOf("Black") }
}

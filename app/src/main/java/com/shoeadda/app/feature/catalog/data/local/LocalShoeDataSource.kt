package com.shoeadda.app.feature.catalog.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/** Reads the shoes that are bundled in the app (`assets/products.json`). Works offline. */
class LocalShoeDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json,
) {
    suspend fun readShoes(): List<LocalShoeDto> = withContext(Dispatchers.IO) {
        val text = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }
        json.decodeFromString<LocalShoesFileDto>(text).shoes
    }

    private companion object {
        const val FILE_NAME = "products.json"
    }
}

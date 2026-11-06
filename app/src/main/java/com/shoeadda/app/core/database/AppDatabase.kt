package com.shoeadda.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shoeadda.app.feature.catalog.data.local.ShoeDao
import com.shoeadda.app.feature.catalog.data.local.ShoeEntity

/**
 * The one app database. Each feature adds its tables here (cart, wishlist, orders, users, ...).
 * While the app is in development, a change to the tables wipes the database instead of migrating it.
 */
@Database(
    entities = [ShoeEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoeDao(): ShoeDao
}

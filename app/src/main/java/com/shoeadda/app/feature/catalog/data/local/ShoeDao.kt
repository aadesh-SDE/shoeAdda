package com.shoeadda.app.feature.catalog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeDao {
    @Query("SELECT * FROM shoes ORDER BY name")
    fun observeAll(): Flow<List<ShoeEntity>>

    @Query("SELECT * FROM shoes WHERE id = :id")
    fun observeById(id: String): Flow<ShoeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(shoes: List<ShoeEntity>)
}

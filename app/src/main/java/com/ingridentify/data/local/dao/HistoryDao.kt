package com.ingridentify.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ingridentify.data.local.entity.HistoryEntity
import com.ingridentify.data.model.RecipeModel

@Dao
interface HistoryDao {
    @Query("SELECT * FROM histories")
    fun getAll(): PagingSource<Int, RecipeModel>

    @Query("DELETE FROM histories")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stories: List<HistoryEntity>)
}
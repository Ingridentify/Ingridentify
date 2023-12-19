package com.ingridentify.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.ingridentify.data.model.RecipeModel

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): PagingSource<Int, RecipeModel>

    //FIXME: the recipe should not be nullable
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getById(id: Int): RecipeModel?
}
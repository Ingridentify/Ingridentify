package com.ingridentify.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ingridentify.data.local.entity.RecipeEntity
import com.ingridentify.data.model.RecipeModel

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): PagingSource<Int, RecipeModel>

    @Query("SELECT * FROM recipes WHERE bookmarked = :bookmarked")
    fun getBookmarked(bookmarked: Boolean): PagingSource<Int, RecipeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("DELETE FROM recipes WHERE bookmarked = 0")
    suspend fun clear()

    @Query("UPDATE recipes SET bookmarked = :bookmarked WHERE id = :id")
    suspend fun setBookmark(id: String, bookmarked: Boolean)

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getById(id: String): RecipeModel

    @Query("SELECT * FROM recipes WHERE name = :name")
    fun getByName(name: String): PagingSource<Int, RecipeModel>
}
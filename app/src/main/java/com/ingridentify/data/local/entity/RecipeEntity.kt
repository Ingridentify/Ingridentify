package com.ingridentify.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val cuisine: String,
    val ingridient: List<String>,
    val recipes: List<String>,
    val urlImage: String
)
package com.ingridentify.data.model

import com.ingridentify.data.local.entity.RecipeEntity

data class RecipeModel(
    val id: String,
    val name: String,
    val cuisine: String,
    val ingridient: List<String>,
    val recipes: List<String>,
    val urlImage: String,
    val bookmarked: Boolean = false
){
    fun toRecipeEntity(): RecipeEntity {
        return RecipeEntity(
            id = id,
            name = name,
            cuisine = cuisine,
            ingridient = ingridient,
            recipes = recipes,
            urlImage = urlImage,
            bookmarked = bookmarked
        )
    }
}

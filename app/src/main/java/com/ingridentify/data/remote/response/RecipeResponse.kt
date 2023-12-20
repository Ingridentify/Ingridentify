package com.ingridentify.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ingridentify.data.local.entity.RecipeEntity

data class RecipeResponse(

	@field:SerializedName("data")
	val data: DataResponse,

	@field:SerializedName("status")
	val status: String
)

data class DataResponse(

	@field:SerializedName("recipes")
	val recipes: List<RecipesItem>
)

data class RecipesItem(

	@field:SerializedName("recipes")
	val recipes: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("cuisine")
	val cuisine: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("urlImage")
	val urlImage: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) {
	fun toRecipeEntity(): RecipeEntity {
		return RecipeEntity(
			id = id,
			name = name,
			cuisine = cuisine,
			recipes = recipes,
			imageUrl = urlImage
		)
	}
}

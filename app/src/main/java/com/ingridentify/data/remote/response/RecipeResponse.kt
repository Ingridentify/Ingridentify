package com.ingridentify.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ingridentify.data.local.entity.HistoryEntity

data class RecipeResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("cuisine")
	val cuisine: String,

	@field:SerializedName("recipes")
	val recipes: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String

) {
	fun toEntity(): HistoryEntity = HistoryEntity(
		id = id,
		name = name,
		cuisine = cuisine,
		recipes = recipes,
		imageUrl = imageUrl
	)
}

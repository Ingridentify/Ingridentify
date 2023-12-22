package com.ingridentify.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ingridentify.data.model.RecipeModel

data class RecipeResponse(

	@field:SerializedName("data")
	val data: List<RecipeModel>,

	@field:SerializedName("status")
	val status: String
)

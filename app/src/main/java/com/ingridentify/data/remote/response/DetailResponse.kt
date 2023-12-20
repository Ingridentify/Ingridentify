package com.ingridentify.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ingridentify.data.model.RecipeModel

data class DetailResponse(

	@field:SerializedName("data")
	val data: RecipeModel,

	@field:SerializedName("status")
	val status: String
)

package com.ingridentify.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("confidence")
	val confidence: String,

	@field:SerializedName("predicted_item")
	val predictedItem: String
)

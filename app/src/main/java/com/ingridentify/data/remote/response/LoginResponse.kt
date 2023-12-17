package com.ingridentify.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("token")
	val token: String
)

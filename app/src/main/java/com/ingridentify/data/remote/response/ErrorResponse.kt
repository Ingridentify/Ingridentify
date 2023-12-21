package com.ingridentify.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("message")
    val message: String? = null,

    //FIXME: does the errorResponse always have this field?
    @field:SerializedName("error")
    val error: String? = null
)
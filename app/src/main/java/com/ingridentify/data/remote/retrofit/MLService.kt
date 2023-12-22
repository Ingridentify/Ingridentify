package com.ingridentify.data.remote.retrofit

import com.ingridentify.data.remote.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MLService {
    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part
    ): PredictResponse
}
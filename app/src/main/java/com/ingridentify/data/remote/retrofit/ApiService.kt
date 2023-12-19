package com.ingridentify.data.remote.retrofit

import com.ingridentify.data.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

//    @GET("histories")
//    suspend fun getHistory(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//        @Query("limit") limit: Int
//    ): List<RecipeResponse>
}
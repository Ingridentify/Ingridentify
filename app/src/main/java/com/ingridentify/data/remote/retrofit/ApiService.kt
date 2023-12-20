package com.ingridentify.data.remote.retrofit

import com.ingridentify.data.remote.response.LoginResponse
import com.ingridentify.data.remote.response.RecipeResponse
import com.ingridentify.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("recipes/{name}")
    suspend fun getRecipe(
        @Header("Authorization") token: String,
        @Path("name") name: String
    ): RecipeResponse

//    @GET("histories")
//    suspend fun getHistory(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//        @Query("limit") limit: Int
//    ): List<RecipeResponse>
}
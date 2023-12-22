package com.ingridentify.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.ingridentify.data.datastore.UserPreference
import com.ingridentify.data.local.IngridentifyDatabase
import com.ingridentify.data.local.entity.RecipeEntity
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.data.model.UserModel
import com.ingridentify.data.remote.response.ErrorResponse
import com.ingridentify.data.remote.response.LoginResponse
import com.ingridentify.data.remote.response.RecipeResponse
import com.ingridentify.data.remote.response.RegisterResponse
import com.ingridentify.data.remote.retrofit.ApiService
import com.ingridentify.data.remote.retrofit.MLService
import com.ingridentify.utils.compress
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class Repository private constructor(
    database: IngridentifyDatabase,
    private val apiService: ApiService,
    private val mlService: MLService,
    private val userPreference: UserPreference
) {
    private val recipeDao = database.recipeDao()

    fun checkSession(): LiveData<UserModel?> = userPreference.getSession().asLiveData()

    fun register(name: String, email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response: RegisterResponse = apiService.register(name, email, password)
            if (response.data != null) {
                emit(Result.Success(response.message))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.error ?: errorBody.message ?: "Something went wrong"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response: LoginResponse = apiService.login(email, password)
            val userData = response.userData
            val userModel = UserModel(
                name = userData.name,
                email = userData.email,
                token = response.token
            )
            userPreference.saveSession(userModel)
            emit(Result.Success(true))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.error ?: errorBody.message ?: "Something went wrong"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun predict(image: File) = liveData {
        emit(Result.Loading)
        try {
            val compressedFile: File = image.compress()
            val imageRequest: RequestBody = compressedFile.asRequestBody("image/*".toMediaType())
            val imageMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData(
                name = "file",
                filename = compressedFile.name,
                body = imageRequest
            )
            val name: String = mlService.predict(imageMultipartBody).predictedItem
            emit(Result.Success(name))
        } catch(e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.error ?: errorBody.message ?: "Something went wrong"))
        } catch(e: Exception) {
            Log.e("Repository", "e.message: ${e.message}")
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getRecipe(name: String) = liveData {
        emit(Result.Loading)
        try {
            val response: RecipeResponse = apiService.getRecipe(userPreference.getToken(), name)
            val recipes = response.data
            val recipeEntities: List<RecipeEntity> = recipes.map { it.toRecipeEntity() }
            recipeDao.clear()
            recipeDao.insertAll(recipeEntities)
            emit(Result.Success(recipes))
        } catch(e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.error ?: errorBody.message ?: "Something went wrong"))
        } catch(e: Exception) {
            Log.e("Repository", "e.message: ${e.message}")
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun logout() {
        userPreference.clearSession()
    }

    fun getRecipes(bookmarked: Boolean): LiveData<PagingData<RecipeModel>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = { recipeDao.getBookmarked(bookmarked) }
    ).liveData

    suspend fun getRecipeDetail(id: String): Result<RecipeModel> {
        return try {
            Result.Success(recipeDao.getById(id))
        } catch(e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            Result.Error(errorBody.error ?: errorBody.message ?: "Something went wrong")
        } catch(e: Exception) {
            Log.e("Repository", "e.message: ${e.message}")
            e.printStackTrace()
            Result.Error(e.message.toString())
        }
    }

    suspend fun setBookmark(id: String, bookmarked: Boolean): Boolean {
        recipeDao.setBookmark(id, bookmarked)
        return isBookmarked(id)
    }

    suspend fun isBookmarked(id: String): Boolean {
        return try {
            recipeDao.getById(id).bookmarked
        } catch(e: Exception) {
            false
        }
    }

    fun getRecipesByName(name: String?): LiveData<PagingData<RecipeModel>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            if (name == null)
                recipeDao.getAll()
            else
                recipeDao.getByName(name)
        }
    ).liveData

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(database: IngridentifyDatabase, apiService: ApiService, mlService: MLService, userPreference: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(database, apiService, mlService, userPreference).apply { instance = this }
            }
    }
}
package com.ingridentify.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ingridentify.data.Repository
import com.ingridentify.data.Result
import com.ingridentify.data.model.RecipeModel

class DetailViewModel(private val repository: Repository) : ViewModel() {

    fun getDetail(id: String): LiveData<Result<RecipeModel>> {
        return repository.getRecipeDetail(id)
    }
}
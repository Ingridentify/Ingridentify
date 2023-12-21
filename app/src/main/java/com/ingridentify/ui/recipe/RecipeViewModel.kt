package com.ingridentify.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ingridentify.data.Repository
import com.ingridentify.data.model.RecipeModel

class RecipeViewModel(private val repository: Repository) : ViewModel() {
    fun getRecipes(name: String?): LiveData<PagingData<RecipeModel>> {
        return repository.getRecipesByName(name).cachedIn(viewModelScope)
    }
}
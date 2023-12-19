package com.ingridentify.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ingridentify.data.Repository
import com.ingridentify.data.model.RecipeModel

class DetailViewModel(private val repository: Repository) : ViewModel() {

    //FIXME: the recipe should not be nullable
    fun getDetail(id: Int): LiveData<RecipeModel?> {
        return repository.getRecipeDetail(id)
    }
}
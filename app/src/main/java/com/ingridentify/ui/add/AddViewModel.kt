package com.ingridentify.ui.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ingridentify.data.Repository
import com.ingridentify.data.Result
import com.ingridentify.data.model.RecipeModel
import java.io.File

class AddViewModel(private val repository: Repository) : ViewModel() {
    private var _imageUri: MutableLiveData<Uri?> = MutableLiveData(null)
    val imageUri: LiveData<Uri?> = _imageUri

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun predict(image: File): LiveData<Result<String>> = repository.predict(image)

    fun getRecipe(name: String): LiveData<Result<List<RecipeModel>>> = repository.getRecipe(name)
}
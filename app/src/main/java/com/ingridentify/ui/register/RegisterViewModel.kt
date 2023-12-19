package com.ingridentify.ui.register

import androidx.lifecycle.ViewModel
import com.ingridentify.data.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}
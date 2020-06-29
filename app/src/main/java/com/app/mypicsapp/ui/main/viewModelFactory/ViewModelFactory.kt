package com.app.mypicsapp.ui.main.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.mypicsapp.data.repository.Repository
import com.app.mypicsapp.ui.main.home.ui.details.DetailsViewModel
import com.app.mypicsapp.ui.main.home.ui.home.HomeViewModel
import com.app.mypicsapp.ui.main.login.ui.login.LoginViewModel
import com.app.mypicsapp.ui.main.login.ui.register.RegisterViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
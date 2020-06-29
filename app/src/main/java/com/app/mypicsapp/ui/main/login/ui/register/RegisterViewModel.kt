package com.app.mypicsapp.ui.main.login.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mypicsapp.data.model.LoginResponse
import com.app.mypicsapp.data.repository.Repository
import com.app.mypicsapp.util.Resource
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {
    private val loginResponse = MutableLiveData<Resource<LoginResponse>>()

    fun registerUser(email: String, password: String, age: Int) = viewModelScope.launch {
        loginResponse.postValue(Resource.loading(null))
        try {
            val responseFromApi = repository.registerRequest(email, password, age)
            loginResponse.postValue(Resource.success(responseFromApi))
        } catch (e: Exception) {
            loginResponse.postValue(Resource.error(e.toString(), null))
        }
    }


    fun getRegisterResponse(): LiveData<Resource<LoginResponse>> {
        return loginResponse
    }
}
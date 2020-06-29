package com.app.mypicsapp.ui.main.login.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mypicsapp.data.model.LoginResponse
import com.app.mypicsapp.data.repository.Repository
import com.app.mypicsapp.util.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private val loginResponse = MutableLiveData<Resource<LoginResponse>>()

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        loginResponse.postValue(Resource.loading(null))
            try {
                val responseFromApi = repository.loginRequest(email, password)
                loginResponse.postValue(Resource.success(responseFromApi))
            } catch (e: Exception) {
                loginResponse.postValue(Resource.error(e.toString(), null))
            }
        }


    fun getLoginResponse(): LiveData<Resource<LoginResponse>> {
        return loginResponse
    }


}
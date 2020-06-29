package com.app.mypicsapp.data.api

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun fetchPhotos(page: Int) = apiService.getUsers(page)
    override suspend fun loginRequest(email: String, password: String) = apiService.loginUser(email, password)
    override suspend fun registerRequest(email: String, password: String, age: Int) = apiService.registerUser(email, password, age)

}
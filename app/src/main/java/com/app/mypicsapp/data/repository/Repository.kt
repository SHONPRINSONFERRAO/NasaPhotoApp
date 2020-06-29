package com.app.mypicsapp.data.repository

import com.app.mypicsapp.data.api.ApiHelper
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun fetchPhotos(page: Int) = apiHelper.fetchPhotos(page)
    suspend fun loginRequest(email: String, password: String) = apiHelper.loginRequest(email, password)
    suspend fun registerRequest(email: String, password: String, age: Int) = apiHelper.registerRequest(email, password, age)

}
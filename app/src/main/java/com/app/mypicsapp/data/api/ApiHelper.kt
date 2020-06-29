package com.app.mypicsapp.data.api

import com.app.mypicsapp.data.model.LoginResponse
import com.app.mypicsapp.data.model.PhotoDataModel

interface ApiHelper {
    suspend fun fetchPhotos(page: Int): PhotoDataModel
    suspend fun loginRequest(email: String, password: String): LoginResponse
    suspend fun registerRequest(email: String, password: String, age: Int): LoginResponse
}
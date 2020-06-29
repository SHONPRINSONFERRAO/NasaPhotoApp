package com.app.mypicsapp.data.api

import com.app.mypicsapp.data.model.LoginResponse
import com.app.mypicsapp.data.model.PhotoDataModel
import retrofit2.http.*

interface ApiService {

    @GET("/api?editors_choice=true")
    suspend fun getUsers(@Query("page") page: Int): PhotoDataModel

    @POST("/login")
    suspend fun loginUser(@Header("email") email: String,
                          @Header("password") password: String): LoginResponse

    @POST("/register")
    suspend fun registerUser(@Header("email") email: String,
                            @Header("password") password: String,
                            @Header("age") age: Int): LoginResponse
}
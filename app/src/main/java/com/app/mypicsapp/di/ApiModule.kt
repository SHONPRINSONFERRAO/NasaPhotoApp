package com.app.mypicsapp.di

import com.app.mypicsapp.BuildConfig
import com.app.mypicsapp.data.api.ApiHelper
import com.app.mypicsapp.data.api.ApiHelperImpl
import com.app.mypicsapp.data.api.ApiService
import com.app.mypicsapp.util.MockResponse
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    private const val BASE_URL = "https://pixabay.com/"
    private const val TAG = "API LOG"

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            //.addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.connectTimeout(5, TimeUnit.SECONDS)

        builder.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val uri = originalHttpUrl.uri().toString()

            if (uri.endsWith("login") || uri.endsWith("register")) {
                val responseString = when {
                    uri.endsWith("login") -> loginResponse(
                        original
                    )
                    uri.endsWith("register") -> registerResponse(
                        original
                    )
                    else -> throw IllegalAccessError("Mocking Failed")
                }

                chain.proceed(chain.request())
                    .newBuilder()
                    .code(MockResponse.SUCCESS_CODE)
                    .protocol(Protocol.HTTP_2)
                    .message(responseString)
                    .body(
                        ResponseBody.create(
                            MediaType.parse("application/json"),
                            responseString.toByteArray()
                        )
                    )
                    .addHeader("content-type", "application/json")
                    .build()

            } else {
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", "16493408-1f01543c145d09e4208784e1f")
                    .build()
                // Request customization: add request headers
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }


        }

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    private fun loginResponse(request: Request): String {
        return with(request.header("email")) {
            when {
                equals("fail@email.com") -> MockResponse.LOGIN_FAILURE
                this!!.contains("mock") -> MockResponse.LOGIN_FAILURE
                else -> MockResponse.LOGIN_SUCCESS
            }
        }
    }

    private fun registerResponse(request: Request): String {
        return with(request.header("email")) {
            when {
                equals("fail@email.com") -> MockResponse.REGISTER_FAILURE
                this!!.contains("mock") -> MockResponse.REGISTER_FAILURE
                else -> MockResponse.REGISTER_SUCCESS
            }
        }
    }

}
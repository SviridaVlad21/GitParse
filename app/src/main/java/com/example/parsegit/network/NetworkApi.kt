package com.example.parsegit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkApi {
    private var mRetrofit : Retrofit
    private const val BASE_URL  = "https://api.github.com/"

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getJsonApi() : JsonAPI{
        return mRetrofit.create(JsonAPI::class.java)
    }
}
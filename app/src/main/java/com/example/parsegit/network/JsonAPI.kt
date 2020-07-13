package com.example.parsegit.network

import com.example.parsegit.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonAPI {
    @GET("users/{userLogin}")
    fun getUser(@Path("userLogin") userLogin : String) : Call<User>
}
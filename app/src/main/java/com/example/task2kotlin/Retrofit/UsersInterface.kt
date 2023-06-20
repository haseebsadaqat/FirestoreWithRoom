package com.example.task2kotlin.Retrofit

import com.example.task2kotlin.CommonKeys
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface UsersInterface {

    @GET("users")
    fun GetuserData(): Call<usersData> }

object RetrofitInstance {
    val usersinterface : UsersInterface by lazy {
        Retrofit.Builder()
            .baseUrl(CommonKeys.ApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsersInterface::class.java)
    }
}
package com.example.tesberanda.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL_VPS = "https://c-greenproject.org:3333/"

    private val retrofitVPS = Retrofit.Builder()
        .baseUrl(BASE_URL_VPS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiServiceInstance: ApiService = retrofitVPS.create(ApiService::class.java)
}

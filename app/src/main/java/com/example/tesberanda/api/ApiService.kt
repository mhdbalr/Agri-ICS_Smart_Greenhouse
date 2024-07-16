package com.example.tesberanda.api

import com.example.tesberanda.DataTopic1Response
import com.example.tesberanda.SensorResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("getOneData")
    fun getDataTopic1(): Call<DataTopic1Response>

    @GET("getdatatable") // Replace with your actual endpoint
    fun getData(): Call<SensorResponse>
}

package com.nytimes.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NYTimesService
{
    private const val httpURL = "https://api.nytimes.com/"

    fun create(): INYTimesService{

        val okHttpClient = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(httpURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(INYTimesService::class.java)
    }

}
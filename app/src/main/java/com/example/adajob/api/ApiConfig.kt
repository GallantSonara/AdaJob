package com.example.adajob.api

import com.example.adajob.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private val loggingInterceptor = if(BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://adajob-6irc7snnoq-uc.a.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    private val retrofitRecommendation: Retrofit = Retrofit.Builder()
        .baseUrl("https://deploy-model-recom-6irc7snnoq-et.a.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val recommendationApiInstance: ApiService = retrofitRecommendation.create(ApiService::class.java)
    val baseApiInstance: ApiService = retrofit.create(ApiService::class.java)
}
package com.example.adajob.api

import com.example.adajob.api.body.LoginRequest
import com.example.adajob.api.body.RecommendationRequest
import com.example.adajob.api.body.RegisterRequest
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.api.response.LoginResponse
import com.example.adajob.api.response.RecommendationResponse
import com.example.adajob.api.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("users")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("joblisting")
    suspend fun listJob() : List<ListJobResponse>

    @POST("/")
    suspend fun recommendationJob(
        @Body recommendationRequest: RecommendationRequest
    ): Response<RecommendationResponse>
}
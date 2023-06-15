package com.example.adajob

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.adajob.api.ApiConfig
import com.example.adajob.api.ApiService
import com.example.adajob.api.body.LoginRequest
import com.example.adajob.api.body.RecommendationRequest
import com.example.adajob.api.body.RegisterRequest
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.api.response.LoginResponse
import com.example.adajob.api.response.RecommendationResponse
import com.example.adajob.api.response.RegisterResponse
import com.example.adajob.dao.JobDatabase
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class Repository(private val apiService: ApiService, private val jobDatabase: JobDatabase) {

    suspend fun userRegister(register: RegisterRequest): Response<RegisterResponse> {
        return ApiConfig.baseApiInstance.registerUser(register)
    }

    suspend fun userLogin(login: LoginRequest): Response<LoginResponse> {
        return ApiConfig.baseApiInstance.loginUser(login)
    }

    fun getList(): LiveData<PagingData<ListJobResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            remoteMediator = com.example.adajob.utils.ListRemoteMediator(
                jobDatabase,
                apiService
            ),
            pagingSourceFactory = {
                jobDatabase.jobDao().getAllJobs()
            }
        ).liveData
    }

    suspend fun getRecommendation(userID: RecommendationRequest): Response<RecommendationResponse> {
        return ApiConfig.recommendationApiInstance.recommendationJob(userID)
    }
}
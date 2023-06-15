package com.example.adajob.ui.recommendation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adajob.Repository
import com.example.adajob.api.ApiConfig
import com.example.adajob.api.body.RecommendationRequest
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.dao.JobDatabase
import com.example.adajob.utils.BaseResponses
import kotlinx.coroutines.launch

class RecommendationViewModel(context: Context) : ViewModel() {

    private val repository = Repository(ApiConfig.recommendationApiInstance, JobDatabase.getDatabase(context))
    private val jobDao = JobDatabase.getDatabase(context).jobDao()

    private val _jobsData = MutableLiveData<BaseResponses<List<ListJobResponse>>>()
    val jobsData: LiveData<BaseResponses<List<ListJobResponse>>> = _jobsData

    fun fetchRecommendationData(userID: RecommendationRequest) {
        getRecommendation(userID)
    }

    private fun getRecommendation(userID: RecommendationRequest) {
        viewModelScope.launch {
            _jobsData.value = BaseResponses.Loading()
            val response = repository.getRecommendation(userID)
            if (response.isSuccessful) {
                val recommendationResponse = response.body()
                recommendationResponse?.let { recommendation ->
                    val taskTitleList = listOf(
                        recommendation.task1,
                        recommendation.task2,
                        recommendation.task3
                    )
                    val jobs = getJobsByTaskTitles(taskTitleList)
                    _jobsData.value = BaseResponses.Success(jobs)
                }
            } else {
                val errorMsg = response.message()
                _jobsData.value = BaseResponses.Error(errorMsg)
            }
        }
    }

    private suspend fun getJobsByTaskTitles(taskTitleList: List<String>): List<ListJobResponse> {
        return jobDao.getJobsByTaskTitles(taskTitleList)
    }
}


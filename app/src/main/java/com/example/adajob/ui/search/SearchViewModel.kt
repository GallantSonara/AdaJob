package com.example.adajob.ui.search

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.dao.JobDao
import com.example.adajob.dao.JobDatabase
import com.example.adajob.utils.BaseResponses
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private var dao: JobDao?
    private var database: JobDatabase?

    private val _searchResult = MutableLiveData<BaseResponses<List<ListJobResponse>>>()
    val searchResult: LiveData<BaseResponses<List<ListJobResponse>>> = _searchResult

    init {
        database = JobDatabase.getDatabase(application)
        dao = database?.jobDao()
    }

    fun searchJobsByTitle(taskTitle: String) {
        viewModelScope.launch {
            try {
                val jobs = dao?.searchJob("%$taskTitle%")
                _searchResult.value = jobs?.let { BaseResponses.Success(it) } ?: BaseResponses.Error("Failed to fetch jobs")
            } catch (e: Exception) {
                _searchResult.value = BaseResponses.Error(e.message)
            }
        }
    }
}

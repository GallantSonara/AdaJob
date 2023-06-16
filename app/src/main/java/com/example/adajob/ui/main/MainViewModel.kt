package com.example.adajob.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.adajob.Repository
import com.example.adajob.api.response.ListJobResponse

class MainViewModel(repository: Repository) : ViewModel() {
    val job: LiveData<PagingData<ListJobResponse>> = repository.getList().cachedIn(viewModelScope)
}
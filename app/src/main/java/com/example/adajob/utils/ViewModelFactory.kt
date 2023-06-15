@file:Suppress("UNCHECKED_CAST")

package com.example.adajob.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adajob.Repository
import com.example.adajob.api.ApiConfig
import com.example.adajob.dao.JobDatabase
import com.example.adajob.ui.auth.login.LoginViewModel
import com.example.adajob.ui.auth.register.RegisterViewModel
import com.example.adajob.ui.detail.DetailViewModel
import com.example.adajob.ui.main.MainViewModel
import com.example.adajob.ui.recommendation.RecommendationViewModel
import com.example.adajob.ui.reminder.ReminderViewModel
import com.example.adajob.ui.search.SearchViewModel

class ViewModelFactory(private val context: Context,private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(context) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(context) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Repository(apiService = ApiConfig.baseApiInstance, JobDatabase.getDatabase(context))) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application) as T
        }
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(application) as T
        }
        if (modelClass.isAssignableFrom(RecommendationViewModel::class.java)) {
            return RecommendationViewModel(context) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
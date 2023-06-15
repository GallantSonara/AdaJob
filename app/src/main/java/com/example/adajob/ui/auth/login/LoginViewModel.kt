package com.example.adajob.ui.auth.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adajob.Repository
import com.example.adajob.api.ApiConfig
import com.example.adajob.api.body.LoginRequest
import com.example.adajob.api.response.LoginResponse
import com.example.adajob.dao.JobDatabase
import com.example.adajob.utils.BaseResponses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(context: Context) : ViewModel() {

    private val userRepo = Repository(ApiConfig.baseApiInstance, JobDatabase.getDatabase(context))
    val loginResult: MutableLiveData<BaseResponses<LoginResponse>> = MutableLiveData()

    fun loginUser(email: String, pass: String){
        loginResult.value = BaseResponses.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, pass)
                val response = withContext(Dispatchers.IO){ userRepo.userLogin(loginRequest)}
                if (response.code() == 200){
                    loginResult.value = BaseResponses.Success(response.body())
                }else{
                    loginResult.value = BaseResponses.Error(response.message())
                }
            }catch (ex: Exception){
                loginResult.value = BaseResponses.Error(ex.message)
            }
        }
    }
}
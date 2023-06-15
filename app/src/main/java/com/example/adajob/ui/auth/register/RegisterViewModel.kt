package com.example.adajob.ui.auth.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adajob.Repository
import com.example.adajob.api.ApiConfig
import com.example.adajob.api.body.RegisterRequest
import com.example.adajob.api.response.RegisterResponse
import com.example.adajob.dao.JobDatabase
import com.example.adajob.utils.BaseResponses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(context: Context) : ViewModel() {
    private val userRepo = Repository(ApiConfig.baseApiInstance,  JobDatabase.getDatabase(context))
    val registerResult: MutableLiveData<BaseResponses<RegisterResponse>> = MutableLiveData()

    fun registerUser(name: String, email: String, pass: String, rePass: String){
        registerResult.value = BaseResponses.Loading()
        viewModelScope.launch {
            try {
                val registerRequest = RegisterRequest(name, email, pass, rePass)
                val response = withContext(Dispatchers.IO){userRepo.userRegister(registerRequest)}
                if (response.code() == 200){
                    registerResult.value = BaseResponses.Success(response.body())
                }else{
                    registerResult.value = BaseResponses.Error(response.message())
                }
            }catch (ex: Exception){
                registerResult.value = BaseResponses.Error(ex.message)
            }
        }
    }
}
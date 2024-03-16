package com.gxsales.client.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gxsales.client.model.dataclass.response.LoginResponse
import com.gxsales.client.model.dataclass.request.LoginRequest
import com.gxsales.client.model.remote.ApiConfig
import retrofit2.Call
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

    private var _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun loginUser(deliveredEmail: String, deliveredPassword: String){
        val loginRequest = LoginRequest(email = deliveredEmail, password = deliveredPassword)
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(loginRequest)
        client.enqueue(object: retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _isFail.value = false
                    _loginResponse.value = response.body()
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}
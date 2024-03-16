package com.gxsales.client.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gxsales.client.model.dataclass.response.LogoutResponse
import com.gxsales.client.model.dataclass.response.ProfileResponse
import com.gxsales.client.model.remote.ApiConfig
import retrofit2.Call
import retrofit2.Response

class DashboardViewModel: ViewModel() {
    private val TAG = DashboardViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

    private var _isUnauthorized = MutableLiveData<Boolean>()
    val isUnauthorized: LiveData<Boolean> = _isFail

    private var _userProfileResponse = MutableLiveData<ProfileResponse>()
    val userProfileResponse: LiveData<ProfileResponse> = _userProfileResponse

    private var _userLogoutResponse = MutableLiveData<LogoutResponse>()
    val userLogoutResponse: LiveData<LogoutResponse> = _userLogoutResponse

    fun getUserProfile(token: String){
        _isLoading.value = true
        val bearerToken = "Bearer $token"

        val client = ApiConfig.getApiService().getUserProfile(bearerToken)

        client.enqueue(object: retrofit2.Callback<ProfileResponse>{
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _isFail.value = false
                    _userProfileResponse.value = response.body()
                    Log.d(TAG, "Success")
                }
                else{
                    if (response.code() == 401) {
                        _isUnauthorized.value = true
                        _isFail.value = false
                        Log.e(TAG, "Unauthorized access: ${response.message()}")
                    } else {
                        _isUnauthorized.value = false
                        _isFail.value = true
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
//                    _isFail.value = true
//                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }

    fun logOutUser(token: String){
        _isLoading.value = true
        val bearerToken = "Bearer $token"
        val client = ApiConfig.getApiService().logoutUser(bearerToken)

        client.enqueue(object: retrofit2.Callback<LogoutResponse>{
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _isFail.value = false
                    _userLogoutResponse.value = response.body()
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}
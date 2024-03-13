package com.gxsales.client.model.remote

import com.gxsales.client.model.dataclass.LoginResponse
import com.gxsales.client.model.dataclass.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}
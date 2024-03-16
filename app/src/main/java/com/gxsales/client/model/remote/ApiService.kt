package com.gxsales.client.model.remote

import com.gxsales.client.model.dataclass.response.LoginResponse
import com.gxsales.client.model.dataclass.request.LoginRequest
import com.gxsales.client.model.dataclass.response.LogoutResponse
import com.gxsales.client.model.dataclass.response.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("logout")
    fun logoutUser(
        @Header("Authorization") userToken: String
    ): Call<LogoutResponse>

    @GET("profile")
    fun getUserProfile(
        @Header("Authorization") userToken: String
    ): Call<ProfileResponse>
}
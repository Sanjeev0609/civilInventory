package com.example.builderstool.network

import com.example.builderstool.network.request.CompanyRegisterRequest
import com.example.builderstool.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("company/login")
    fun adminLogin(@Body loginRequest: CompanyRegisterRequest): Call<UserResponse>
}
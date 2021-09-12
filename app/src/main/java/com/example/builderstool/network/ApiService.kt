package com.example.builderstool.network

import com.example.builderstool.model.Product
import com.example.builderstool.network.request.CompanyLoginRequest
import com.example.builderstool.network.request.CompanyRegisterRequest
import com.example.builderstool.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("company/login")
    fun adminLogin(@Body loginRequest: CompanyLoginRequest): Call<UserResponse>

    @POST("company/register")
    fun adminRegister(@Body loginRequest: CompanyRegisterRequest ): Call<UserResponse>

    @GET("products")
    fun listProducts(@Query("company_id") id:Int):Call<ArrayList<Product>>

}
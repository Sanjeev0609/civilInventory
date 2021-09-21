package com.example.builderstool.network

import com.example.builderstool.model.Product
import com.example.builderstool.model.Purchase
import com.example.builderstool.model.Site
import com.example.builderstool.model.Supplier
import com.example.builderstool.network.request.*
import com.example.builderstool.network.response.CommonResponse
import com.example.builderstool.network.response.PurchaseResponse
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
    fun listProducts():Call<ArrayList<Product>>

    @POST("products")
    fun createProduct( @Body addProductRequest: AddProductRequest):Call<CommonResponse>

    @POST("sites")
    fun createSite(@Body addSiteRequest: AddSiteRequest):Call<CommonResponse>

    @POST("suppliers")
    fun createSupplier(@Body addSupplierRequest: AddSupplierRequest):Call<CommonResponse>

    @GET("sites")
    fun listSites():Call<ArrayList<Site>>

    @GET("suppliers")
    fun listSuppliers():Call<ArrayList<Supplier>>

    @POST("purchase")
    fun createPurchase(@Body purchaseRequest: Purchase):Call<PurchaseResponse>
}
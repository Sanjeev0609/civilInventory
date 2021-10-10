package com.example.builderstool.network

import com.example.builderstool.model.*
import com.example.builderstool.network.request.*
import com.example.builderstool.network.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

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

    @GET("purchase")
    fun listPurchases():Call<ArrayList<Purchase>>
    @POST("order")
    fun createOrder(@Body orderRequest: Order):Call<OrderResponse>
    @GET("order")
    fun listOrders(@Query("customer_id")customerId:Int):Call<ArrayList<Order>>
    @GET("order/{id}/products")
    fun getOrderProducts(@Path("id")id:Int):Call<ArrayList<PurchaseProducts>>

    @PUT("order/{id}/edit")
    fun editOrder(@Path("id")id:Int,@Body editOrderRequest: EditOrderRequest):Call<CommonResponse>

    @GET("products/search")
    fun searchProduct(@Query("search")query:String):Call<SearchResponse>

    @GET("purchase/balances")
    fun getPurchaseBalances():Call<ArrayList<PurchaseBalance>>
    @GET("order/balances")
    fun getOrderBalances():Call<ArrayList<OrderBalance>>
    @POST("purchase/update-balance")
    fun updatePurchaseBalance(@Body updateBalanceRequest: UpdateBalanceRequest):Call<CommonResponse>

    @POST("order/update-balance")
    fun updateOrderBalance(@Body updateBalanceRequest: UpdateBalanceRequest):Call<CommonResponse>

}
package com.example.builderstool.ui.orders

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.common.PdfUtils
import com.example.builderstool.model.Order
import com.example.builderstool.model.Product
import com.example.builderstool.model.Purchase
import com.example.builderstool.model.Supplier
import com.example.builderstool.network.response.OrderResponse
import com.example.builderstool.network.response.PurchaseResponse
import retrofit2.Call
import retrofit2.Response

class OrderViewModel(application: Application):BaseViewModel(application) {
    var products= MutableLiveData<ArrayList<Product>>()
    var orderData=Order()




    fun listProducts(){
        isProgressShowing.value=true
        BaseApplication.getInstance().apimanager.listProducts(object :retrofit2.Callback<ArrayList<Product>>{
            override fun onResponse(
                call: Call<ArrayList<Product>>,
                response: Response<ArrayList<Product>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    products.value=response.body()
                }else{

                }
            }

            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun createOrder(){
        isProgressShowing.value=true
        BaseApplication.getInstance().apimanager.createOrder(orderData,object :retrofit2.Callback<OrderResponse>{
            override fun onResponse(
                call: Call<OrderResponse>,
                response: Response<OrderResponse>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    apiResponseMessage.value=response.body()!!.message
                    orderData.id=response.body()!!.orderId
                    PdfUtils().createOrderBill(BaseApplication.getInstance(),orderData)
//                    PdfUtils().createBill(BaseApplication.getInstance(),orderData)
                }else{

                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}
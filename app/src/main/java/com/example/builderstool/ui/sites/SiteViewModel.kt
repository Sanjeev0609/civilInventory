package com.example.builderstool.ui.sites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.model.Order
import com.example.builderstool.model.Purchase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SiteViewModel(application: Application):BaseViewModel(application) {
    val orders= MutableLiveData<ArrayList<Order>>()

    fun listOrders(customerId:Int){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().listOrder(customerId,object :
            Callback<ArrayList<Order>> {
            override fun onResponse(
                call: Call<ArrayList<Order>>,
                response: Response<ArrayList<Order>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    orders.value=response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<Order>>, t: Throwable) {
                apiResponseMessage.value=t.message.toString()
            }
        })
    }
}
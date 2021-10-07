package com.example.builderstool.ui.orders

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.model.PurchaseProducts
import com.example.builderstool.network.request.EditOrderRequest
import com.example.builderstool.network.response.CommonResponse
import com.example.builderstool.network.response.ListOrderProductsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditOrderViewModel(application: Application):BaseViewModel(application) {
    val products =MutableLiveData<ArrayList<PurchaseProducts>>()

    fun listProducts(id:Int){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().listOrderProducts(id,object :Callback<ArrayList<PurchaseProducts>>{
            override fun onResponse(
                call: Call<ArrayList<PurchaseProducts>>,
                response: Response<ArrayList<PurchaseProducts>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    products.value=response.body()
                }else{
                   apiResponseMessage.value="Error"
                }
            }

            override fun onFailure(call: Call<ArrayList<PurchaseProducts>>, t: Throwable) {
                isProgressShowing.value=false
                Log.d("FAILURE",t.message.toString())
            }
        })
    }
    fun editOrder(id:Int,editOrderRequest: EditOrderRequest){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().editOrder(id,editOrderRequest,object :Callback<CommonResponse>{
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    apiResponseMessage.value=response.body()!!.message
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                isProgressShowing.value=false
                Log.d("FAILURE",t.message.toString())
            }
        })
    }

}
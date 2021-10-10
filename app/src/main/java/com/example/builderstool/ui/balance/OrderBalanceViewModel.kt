package com.example.builderstool.ui.balance

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseActivity
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.network.request.UpdateBalanceRequest
import com.example.builderstool.network.response.CommonResponse
import com.example.builderstool.network.response.OrderBalance
import com.example.builderstool.network.response.PurchaseBalance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Array

class OrderBalanceViewModel (application: Application):BaseViewModel(application){
    var balances=MutableLiveData<ArrayList<OrderBalance>>()
    fun getBalances(){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().getOrderBalances(object :Callback<ArrayList<OrderBalance>>{
            override fun onResponse(
                call: Call<ArrayList<OrderBalance>>,
                response: Response<ArrayList<OrderBalance>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    balances.value=response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<OrderBalance>>, t: Throwable) {
                isProgressShowing.value=false
                apiResponseMessage.value=t.message.toString()
            }
        })
    }

    fun updateBalance(updateBalanceRequest: UpdateBalanceRequest){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().updateOrderBalance(updateBalanceRequest,object:Callback<CommonResponse>{
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    isProgressShowing.value=false
                    apiResponseMessage.value=response.body()!!.message
                }else{
                    apiResponseMessage.value="Error"
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
               isProgressShowing.value=false
                apiResponseMessage.value=t.message.toString()
            }
        })
    }
}
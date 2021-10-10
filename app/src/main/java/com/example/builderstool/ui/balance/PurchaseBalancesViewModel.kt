package com.example.builderstool.ui.balance

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseActivity
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.network.request.UpdateBalanceRequest
import com.example.builderstool.network.response.CommonResponse
import com.example.builderstool.network.response.PurchaseBalance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Array

class PurchaseBalancesViewModel (application: Application):BaseViewModel(application){
    var balances=MutableLiveData<ArrayList<PurchaseBalance>>()
    fun getBalances(){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().getPurchaseBalances(object :Callback<ArrayList<PurchaseBalance>>{
            override fun onResponse(
                call: Call<ArrayList<PurchaseBalance>>,
                response: Response<ArrayList<PurchaseBalance>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    balances.value=response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<PurchaseBalance>>, t: Throwable) {
                isProgressShowing.value=false
                apiResponseMessage.value=t.message.toString()
            }
        })
    }
    fun updateBalance(updateBalanceRequest: UpdateBalanceRequest){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().updatePurchaseBalance(updateBalanceRequest,object:Callback<CommonResponse>{
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
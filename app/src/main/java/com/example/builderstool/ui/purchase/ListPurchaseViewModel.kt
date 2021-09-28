package com.example.builderstool.ui.purchase

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.model.Purchase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPurchaseViewModel(application: Application):BaseViewModel(application) {
    val purchases= MutableLiveData<ArrayList<Purchase>>()

    fun listPurchases(){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().listPurchase(object :Callback<ArrayList<Purchase>>{
            override fun onResponse(
                call: Call<ArrayList<Purchase>>,
                response: Response<ArrayList<Purchase>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    purchases.value=response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<Purchase>>, t: Throwable) {
                apiResponseMessage.value=t.message.toString()
            }
        })
    }
}
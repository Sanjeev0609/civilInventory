package com.example.builderstool.ui.purchase

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.model.Product
import com.example.builderstool.model.Purchase
import com.example.builderstool.model.Supplier
import com.example.builderstool.network.response.PurchaseResponse
import retrofit2.Call
import retrofit2.Response

class PurchaseViewModel(application: Application):BaseViewModel(application) {
    var suppliers= MutableLiveData<ArrayList<Supplier>>()
    var products=MutableLiveData<ArrayList<Product>>()
    var purchaseData=ArrayList<Purchase>()

    fun listSuppliers(){
        isProgressShowing.value=true
        BaseApplication.getInstance().apimanager.listSuppliers(object :retrofit2.Callback<ArrayList<Supplier>>{
            override fun onResponse(
                call: Call<ArrayList<Supplier>>,
                response: Response<ArrayList<Supplier>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    suppliers.value=response.body()
                }else{

                }
            }

            override fun onFailure(call: Call<ArrayList<Supplier>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


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

    fun createPurchase(){
        isProgressShowing.value=true
        BaseApplication.getInstance().apimanager.createPurchase(purchaseData[0],object :retrofit2.Callback<PurchaseResponse>{
            override fun onResponse(
                call: Call<PurchaseResponse>,
                response: Response<PurchaseResponse>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    apiResponseMessage.value=response.body()!!.message
                }else{

                }
            }

            override fun onFailure(call: Call<PurchaseResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}
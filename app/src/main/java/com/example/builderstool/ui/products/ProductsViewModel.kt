package com.example.builderstool.ui.products

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.model.Product
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ProductsViewModel(application: Application):BaseViewModel(application) {
    var products=MutableLiveData<ArrayList<Product>>()

    fun listProducts(){
        isProgressShowing.value=true
        BaseApplication.getInstance().apimanager.listProducts(1,object :retrofit2.Callback<ArrayList<Product>>{
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
}
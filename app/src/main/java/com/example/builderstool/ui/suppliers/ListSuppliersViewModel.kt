package com.example.builderstool.ui.suppliers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.model.Site
import com.example.builderstool.model.Supplier
import retrofit2.Call
import retrofit2.Response

class ListSuppliersViewModel(application: Application):BaseViewModel(application) {
    var suppliers= MutableLiveData<ArrayList<Supplier>>()

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
}
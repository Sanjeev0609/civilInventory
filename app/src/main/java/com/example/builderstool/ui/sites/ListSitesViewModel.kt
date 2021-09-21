package com.example.builderstool.ui.sites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.model.Site
import retrofit2.Call
import retrofit2.Response

class ListSitesViewModel(application: Application):BaseViewModel(application) {
    var sites=MutableLiveData<ArrayList<Site>>()

    fun listSites(){
        isProgressShowing.value=true
        BaseApplication.getInstance().apimanager.listSites(object :retrofit2.Callback<ArrayList<Site>>{
            override fun onResponse(
                call: Call<ArrayList<Site>>,
                response: Response<ArrayList<Site>>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    sites.value=response.body()
                }else{

                }
            }

            override fun onFailure(call: Call<ArrayList<Site>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
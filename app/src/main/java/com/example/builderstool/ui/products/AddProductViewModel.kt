package com.example.builderstool.ui.products

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseActivity
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.network.ErrorHandle
import com.example.builderstool.network.request.AddProductRequest
import com.example.builderstool.network.response.CommonResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductViewModel(application: Application):BaseViewModel(application) {

    var createProductRequest=AddProductRequest()
    var nameError=MutableLiveData<String>()
    var priceError=MutableLiveData<String>()
    var unitError=MutableLiveData<String>()
    var stockError =MutableLiveData<String>()
    fun createProduct(){
        isProgressShowing.value=true
        BaseApplication.getInstance().apimanager.createProduct(1,createProductRequest,object :Callback<CommonResponse>{
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    apiResponseMessage.value=response.body()!!.message
                }else{
                    var errorBodyString = response.errorBody()!!.string()
                    Log.d("LoginError", errorBodyString)
                    if (response.code() == 404 || response.code() == 500 || response.code() == 401) {
                        var errorString = ErrorHandle.parseError(
                            response,
                            errorBodyString
                        ) as? String?
                        handleApiResponse(errorString)
                    } else {
                        val responseObject: JsonObject = JsonParser().parse(errorBodyString).getAsJsonObject()
                        if (responseObject.has("errors")) {
                            val errorObject = responseObject.getAsJsonObject("errors")
                            if (errorObject.has("name")) {
                               nameError.setValue(errorObject.getAsJsonArray("name")[0].asString)
                            }
                            if (errorObject.has("unit")) {
                                unitError.setValue(errorObject.getAsJsonArray("unit")[0].asString)
                            }
                            if (errorObject.has("stock")) {
                                stockError.setValue(errorObject.getAsJsonArray("stock")[0].asString)
                            }
                            if (errorObject.has("price")) {
                                priceError.setValue(errorObject.getAsJsonArray("price")[0].asString)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
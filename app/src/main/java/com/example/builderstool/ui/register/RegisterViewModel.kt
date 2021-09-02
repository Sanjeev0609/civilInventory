package com.example.builderstool.ui.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.BaseViewModel
import com.example.builderstool.network.ErrorHandle
import com.example.builderstool.network.request.CompanyRegisterRequest
import com.example.builderstool.network.response.UserResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel (application: Application):BaseViewModel(application){
    val mobileError by lazy {
        MutableLiveData<String>()
    }
    val passwordError by lazy {
        MutableLiveData<String>()
    }
    val nameError by lazy {
        MutableLiveData<String>()
    }
    val emailError by lazy {
        MutableLiveData<String>()
    }
    val navigateToMain by lazy {
        MutableLiveData<Boolean>()
    }

    var registerRequest= CompanyRegisterRequest()
    fun adminRegister(){
        isProgressShowing.value=true
        BaseApplication.getInstance().getApiManager().adminRegister(registerRequest,object :
            Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                isProgressShowing.value=false
                if(response.isSuccessful){
                    var user=response.body()
//                user!!.userType=userType
                    Log.d("Login", "Login success")
                    handleApiResponse("Login Successfull")
//                SharedPreferenceManager(BaseApplication.getInstance()).userLogin(user)
                    navigateToMain.value=true
                } else {
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
                            if (errorObject.has("mobile")) {
                                mobileError.setValue(errorObject.getAsJsonArray("mobile")[0].asString)
                            }
                            if (errorObject.has("password")) {
                                passwordError.setValue(errorObject.getAsJsonArray("password")[0].asString)
                            }
                            if (errorObject.has("name")) {
                                nameError.setValue(errorObject.getAsJsonArray("name")[0].asString)
                            }
                            if (errorObject.has("email")) {
                                emailError.setValue(errorObject.getAsJsonArray("email")[0].asString)
                            }
                        }
                    }
                }

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}
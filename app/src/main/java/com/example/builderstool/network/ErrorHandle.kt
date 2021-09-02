package com.example.builderstool.network



import android.util.Log
import com.example.builderstool.R
import com.example.builderstool.common.BaseApplication
import com.example.builderstool.common.Constants
import com.google.gson.Gson
import com.google.gson.JsonParser

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit

object ErrorHandle {
    private val TAG = ErrorHandle::class.java.simpleName
    private const val ERROR_CODE_422 = 422

    fun parseError(
        response: Response<*>,errorBodyString:String

    ): Any? {
        try {
            val retrofitClient: Retrofit = RetrofitService.getRetrofit()
            if (response.code()==404||response.code()==500||response.code()==401||response.code()==400||response.code()==419) {
                var error = Gson().fromJson(
                    errorBodyString,
                    ErrorResponse::class.java
                )
                Log.d("LoginError","error body at 404 : "+errorBodyString)
                Log.d(
                    "LoginError",
                    "error message=" + error?.message.toString()
                )
                return error.message

            }else if (response.code() == ERROR_CODE_422) {
                response.errorBody()?.let {
                    val errorBody = JsonParser().parse(errorBodyString).asJsonObject
                    if (errorBody.has(Constants.KEY_ERRORS))
                        return errorBody.getAsJsonObject(Constants.KEY_ERRORS)
                }
            } else {
                val converter: Converter<ResponseBody?, ErrorResponse> =
                    retrofitClient.responseBodyConverter(
                        ErrorResponse::class.java,
                        arrayOfNulls(0)
                    )
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Log.d(TAG,"error body not null")
                    Log.d(TAG,errorBody.string())
                    val errorResponse: ErrorResponse? = converter.convert(errorBody)
                    errorResponse?.message?.let { return it }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getErrorResponse: Caught exception: " + e.message, e)
        }
        return BaseApplication.getInstance().resources.getString(R.string.something_went_wrong)
    }
    class ErrorResponse{
        var message=""
    }
}
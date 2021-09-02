package com.example.builderstool.network

import android.util.Log
import com.example.builderstool.network.request.CompanyLoginRequest
import com.example.builderstool.network.request.CompanyRegisterRequest
import com.example.builderstool.network.response.UserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


private const val baseUrl = "https://civil-inventory.herokuapp.com/"
class RetrofitService {
    companion object {
        const val BEARER="Bearer "
        const val AUTHORISATION_KEY="Authorization"
        private const val TIMEOUT:Long=30
        private const val UNAUTHORISED_CODE=401
        fun getRetrofit(): Retrofit {
            var interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val headerInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    request = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json").build()
                    val response = chain.proceed(request)
                    return response
                }
            }
            val httpClient =
                OkHttpClient.Builder().addInterceptor(headerInterceptor).addInterceptor(interceptor).readTimeout(
                    TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)



            val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
                GsonConverterFactory.create()
            ).client(httpClient.build()).build()
            return retrofit
        }
        fun retrofit(): ApiService {
            var interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val headerInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    request = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json").build()
                    val response = chain.proceed(request)
                    return response
                }
            }
            val httpClient =
                OkHttpClient.Builder().addInterceptor(headerInterceptor).addInterceptor(interceptor).addInterceptor(AuthorizationInterceptor()).readTimeout(
                    TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)



            val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
                GsonConverterFactory.create()
            ).client(httpClient.build()).build()
            val service = retrofit.create(ApiService::class.java)
            return service
        }
        private fun <T> request(call: Call<T>, callback: Callback<T>) {
            call.enqueue(callback)
        }
    }
    fun adminLogin(loginRequest: CompanyLoginRequest,callback: Callback<UserResponse>){
        request(retrofit().adminLogin(loginRequest),callback)
    }
    fun adminRegister(loginRequest: CompanyRegisterRequest,callback: Callback<UserResponse>){
        request(retrofit().adminRegister(loginRequest),callback)
    }

    class AuthorizationInterceptor : Interceptor {
        private val TAG: String = AuthorizationInterceptor::class.java.simpleName

        override fun intercept(chain: Interceptor.Chain): Response {
            Log.d(
                TAG,
                "LMAuthorizationInterceptor: called"
            )
            val request = chain.request()
            var response = chain.proceed(request)
            try {
                if (response.code == UNAUTHORISED_CODE) {
                    synchronized(this) {
                        logoutUser()
//                        if (isRefreshTokenGoingOn) {
//                            pendingRequests.add(request)
//                        } else {
//                            response = retry(request, chain, response)
//                        }
                    }
                }else if (response.code==404){
//                    Log.d("Retrofit",response.body?.string())
                }
            } catch (e: IOException) {
                Log.e(
                    TAG, "intercept: IOException: ", e
                )
            }
            return response
        }

//        private fun retry(
//            request: Request,
//            chain: Interceptor.Chain,
//            response: Response
//        ): Response {
//            isRefreshTokenGoingOn = true
//            LMApplication.getInstance().getPreference().refreshToken?.let {
//                val refreshTokenCall: Call<ResponseBody> = getRefreshToken().refreshToken()
//                val refreshTokenResponse = refreshTokenCall.execute()
//                when {
//                    refreshTokenResponse.isSuccessful ->{
//                        LMLog.d(TAG, "LMAuthorizationInterceptor: intercept: success")
//                        LMUserRepository.setTokens(refreshTokenResponse.headers())
//                        var headers=refreshTokenResponse.headers()
//                        LMLog.d("ApiManager",headers["subscription_status"].toString())
//                        if( headers["subscription_status"]== ACTIVE){
//                            if (LMApplication.getInstance().getPreference().refreshToken != null) {
//                                if (pendingRequests.size > 0) {
//                                    for (currentRequest in pendingRequests) {
//                                        getRequest(
//                                            currentRequest,
//                                            isAuthorizedClient = true,
//                                            addRefresh = false
//                                        )
//                                    }
//                                    pendingRequests.clear()
//                                }
//                                isRefreshTokenGoingOn = false
//                                val authorizationRequest: Request = getRequest(
//                                    request,
//                                    isAuthorizedClient = true,
//                                    addRefresh = false
//                                )
//                                return chain.proceed(authorizationRequest)
//                            } else {
//                                logoutUser()
//                            }
//                        }else{
//                            isRefreshTokenGoingOn=false
//                            LMLog.d("Logout","Subscription Ended")
//                            SubscriptionEnded()
//                            LMApplication.getInstance().getPreference().subscriptionStatus="inactive"
//                        }
//                    }
//                    refreshTokenResponse.code() == UNAUTHORISED_CODE -> {
//                        isRefreshTokenGoingOn = false
//                        LMLog.d(
//                            TAG,
//                            "LMAuthorizationInterceptor: intercept: 401, invalidating session"
//                        )
//                        logoutUser()
//                    }
//                    else -> {
//                        isRefreshTokenGoingOn = false
//                        LMLog.d(
//                            TAG,
//                            "LMAuthorizationInterceptor: intercept: else called. Login Retry problem"
//                        )
//                    }
//                }
//            } ?: logoutUser()
//            return response
//        }

        private fun logoutUser() {
//                EventBus.getDefault().post(TokenExpiredEvent())
        }
//        private fun SubscriptionEnded() {
//            EventBus.getDefault().post(LMSubscriptionEndEvent())
//        }

    }
}
